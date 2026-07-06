package com.JohnBieniek.Java26Demo.service;

import static com.JohnBieniek.Java26Demo.utility.SqlFormattingUtils.formatJoinedEmployeeAndTeam;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.JohnBieniek.Java26Demo.dto.organization.AddEmployeeRequest;
import com.JohnBieniek.Java26Demo.dto.organization.EmployeeProjectAssignment;
import com.JohnBieniek.Java26Demo.dto.organization.EmployeeSummary;
import com.JohnBieniek.Java26Demo.dto.organization.ProjectSummary;
import com.JohnBieniek.Java26Demo.dto.organization.TeamEmployeeStatistics;
import com.JohnBieniek.Java26Demo.model.organization.Employee;
import com.JohnBieniek.Java26Demo.model.organization.Project;
import com.JohnBieniek.Java26Demo.model.organization.Team;
import com.JohnBieniek.Java26Demo.repository.EmployeeRepository;
import com.JohnBieniek.Java26Demo.repository.ProjectRepository;
import com.JohnBieniek.Java26Demo.repository.TeamRepository;

@Service
public class SqlService {
    private static final Logger logger = LoggerFactory.getLogger(SqlService.class);

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final JdbcTemplate jdbcTemplate;

    public SqlService(EmployeeRepository employeeRepository,
                      ProjectRepository projectRepository,
                      TeamRepository teamRepository,
                      JdbcTemplate jdbcTemplate) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates an employee for an existing team; used by the POST creation endpoint.
     */
    public String addEmployee(AddEmployeeRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found."));

        employeeRepository.save(new Employee(
            request.getName(),
            request.getDepartment(),
            request.getSalary(),
            request.getBonus(),
            request.getPhoneNumber(),
            null,
            team
        ));

        return "Employee added successfully.";
    }

    /**
     * Creates a project and assigns it to an existing team; used by the POST creation endpoint.
     */
    public String addProject(String name, int budget, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found."));
        Project project = projectRepository.save(new Project(name, budget, team));
        return "Project added successfully with id " + project.getId() + ".";
    }

    /**
     * Creates a team; used by the POST creation endpoint.
     */
    public String addTeam(String name, String location) {
        Team team = teamRepository.save(new Team(name, location));
        return "Team added successfully with id " + team.getId() + ".";
    }

    /**
     * Performs a parameterized soft delete, preserving the employee row for auditability
     * while preventing SQL injection through bound JDBC parameters.
     */
    public void deleteEmployee(Long employeeId) {
        int updated = jdbcTemplate.update(
                "UPDATE employee SET deleted_at = ? WHERE id = ?",
                LocalDateTime.now(),
                employeeId
        );

        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found with id " + employeeId);
        }
    }

    /**
     * Demonstrates UNION ALL by combining a LEFT JOIN result with unmatched rows
     * found through a RIGHT JOIN.
     */
    public String getAllEmployeesAndTeamsUnionAll() {
        String query = """
                SELECT
                    e.name AS employee_name,
                    t.name AS team_name,
                    'employee with optional team' AS row_source
                FROM employee e
                LEFT JOIN team t ON e.team_id = t.id

                UNION ALL

                SELECT
                    e.name AS employee_name,
                    t.name AS team_name,
                    'team without employees' AS row_source
                FROM employee e
                RIGHT JOIN team t ON e.team_id = t.id
                WHERE e.id IS NULL

                ORDER BY team_name, employee_name
                """;

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> rs.getString("row_source")
                    + " | "
                    + formatJoinedEmployeeAndTeam(
                            rs.getString("employee_name"),
                            rs.getString("team_name"))
        ).toString();
    }

    /**
     * Returns employees that have been soft deleted.
     */
    public String getDeletedEmployees() {
        List<String> rows = jdbcTemplate.query(
            "SELECT e.name, e.deleted_at FROM employee e WHERE e.deleted_at IS NOT NULL",
            (rs, rowNum) -> rs.getString("name") + " | deleted at " + rs.getTimestamp("deleted_at")
        );

        logger.info("Deleted employee query result: {}", rows);
        return rows.toString();
    }

    /**
     * Demonstrates duplicate detection with ROW_NUMBER, PARTITION BY, and an outer
     * query that retains occurrences after the first record.
     */
    public String getDuplicateEmployeePhoneNumbers() {
        String query = """
                SELECT
                    id,
                    name,
                    phone_number,
                    row_num
                FROM (
                    SELECT
                        e.id,
                        e.name,
                        e.phone_number,
                        ROW_NUMBER() OVER (
                            PARTITION BY e.phone_number
                            ORDER BY e.id
                        ) AS row_num
                    FROM employee e
                    WHERE e.phone_number IS NOT NULL
                      AND e.deleted_at IS NULL
                ) ranked_employees
                WHERE row_num > 1
                ORDER BY phone_number, row_num
                """;

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> "employee id: " + rs.getLong("id")
                    + " | name: " + rs.getString("name")
                    + " | duplicate phone: " + rs.getString("phone_number")
                    + " | occurrence: " + rs.getInt("row_num")
        ).toString();
    }

    /**
     * Demonstrates COALESCE by replacing missing employee phone numbers.
     */
    public String getEmployeePhoneNumbers() {
        String query = "SELECT e.name, COALESCE(e.phone_number, 'No phone') AS phoneNumber "
                + "FROM employee e "
                + "WHERE e.deleted_at IS NULL "
                + "ORDER BY e.name";

        List<String> rows = jdbcTemplate.query(
            query,
            (rs, rowNum) -> rs.getString("name") + " | " + rs.getString("phoneNumber")
        );

        logger.info("Employee phone number query result: {}", rows);
        return rows.toString();
    }

    /**
     * Demonstrates joining employee, team, and project tables and maps each row to
     * immutable Java record projections.
     */
    public List<EmployeeProjectAssignment> getEmployeesTeamsAndProjects() {
        String query = """
                SELECT
                    e.id AS employee_id,
                    e.name AS employee_name,
                    e.department,
                    t.name AS team_name,
                    p.id AS project_id,
                    p.name AS project_name,
                    p.budget AS project_budget
                FROM employee e
                INNER JOIN team t ON e.team_id = t.id
                INNER JOIN project p ON p.team_id = t.id
                WHERE e.deleted_at IS NULL
                ORDER BY t.name, p.name, e.name
                """;

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> new EmployeeProjectAssignment(
                    new EmployeeSummary(
                            rs.getLong("employee_id"),
                            rs.getString("employee_name"),
                            rs.getString("department"),
                            rs.getString("team_name")),
                    new ProjectSummary(
                            rs.getLong("project_id"),
                            rs.getString("project_name"),
                            rs.getInt("project_budget"),
                            rs.getString("team_name")))
        );
    }

    /**
     * Demonstrates INNER JOIN by returning only employees with matching teams.
     */
    public String getEmployeesWithTeamsInnerJoin() {
        String query = "SELECT e.name AS employee_name, t.name AS team_name "
                + "FROM employee e "
                + "INNER JOIN team t ON e.team_id = t.id "
                + "ORDER BY t.name, e.name";

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> rs.getString("employee_name") + " | " + rs.getString("team_name")
        ).toString();
    }

    /**
     * Demonstrates WHERE filtering before grouping and HAVING filtering after
     * aggregate compensation is calculated.
     */
    public String getHighlyCompensatedEmployees() {
        String query = "SELECT e.name, t.name AS teamName, e.department, "
                + "COALESCE(e.phone_number, 'No phone') AS phoneNumber, "
                + "SUM(e.salary + e.bonus) AS totalCompensation "
                + "FROM employee e "
                + "JOIN team t ON e.team_id = t.id "
                + "WHERE e.salary >= 90000 "
                + "AND e.deleted_at IS NULL "
                + "GROUP BY e.department, t.name, e.name, e.phone_number "
                + "HAVING SUM(e.salary + e.bonus) >= 100000 "
                + "ORDER BY totalCompensation DESC "
                + "LIMIT 10";

        List<String> rows = jdbcTemplate.query(
            query,
            (rs, rowNum) -> rs.getString("name") + " | "
                    + rs.getString("teamName") + " | "
                    + rs.getString("department") + " | "
                    + rs.getString("phoneNumber") + " | "
                    + rs.getInt("totalCompensation")
        );

        logger.info("SQL demo query result: {}", rows);
        if (rows.isEmpty()) {
            return "No highly compensated employees found. Run /demo/resetEmployeesTeamsAndProjects "
                    + "to populate the database or add more employees with higher compensation.";
        }
        return rows.toString();
    }

    EmployeeProjectAssignment findEmployeeProjectAssignment(Long employeeId, Long projectId) {
        String query = """
                SELECT
                    e.id AS employee_id,
                    e.name AS employee_name,
                    e.department,
                    t.name AS team_name,
                    p.id AS project_id,
                    p.name AS project_name,
                    p.budget AS project_budget
                FROM employee e
                INNER JOIN team t ON e.team_id = t.id
                INNER JOIN project p ON p.team_id = t.id
                WHERE e.id = ?
                  AND p.id = ?
                  AND e.deleted_at IS NULL
                """;

        List<EmployeeProjectAssignment> assignments = jdbcTemplate.query(
            query,
            (rs, rowNum) -> new EmployeeProjectAssignment(
                    new EmployeeSummary(
                            rs.getLong("employee_id"),
                            rs.getString("employee_name"),
                            rs.getString("department"),
                            rs.getString("team_name")),
                    new ProjectSummary(
                            rs.getLong("project_id"),
                            rs.getString("project_name"),
                            rs.getInt("project_budget"),
                            rs.getString("team_name"))),
            employeeId,
            projectId
        );

        if (assignments.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No active employee/project assignment found for those ids.");
        }

        return assignments.getFirst();
    }

    /**
     * Returns project data as immutable ProjectSummary records, demonstrating
     * records as concise API response projections.
     */
    public List<ProjectSummary> getProjects() {
        return projectRepository.findAll().stream()
                .map(project -> new ProjectSummary(
                        project.getId(),
                        project.getName(),
                        project.getBudget(),
                        project.getTeam().getName()))
                .toList();
    }

    /**
     * Demonstrates COUNT, SUM, AVG, MIN, MAX, GROUP BY, and JOIN, mapping each
     * aggregate result to an immutable TeamEmployeeStatistics record.
     */
    public List<TeamEmployeeStatistics> getTeamEmployeeStatistics() {
        String query = "SELECT t.name AS team_name, "
                + "COUNT(*) AS employee_count, "
                + "SUM(e.salary + e.bonus) AS total_compensation, "
                + "AVG(e.salary + e.bonus) AS avg_compensation, "
                + "MIN(e.salary) AS lowest_salary, "
                + "MAX(e.salary) AS highest_salary "
                + "FROM employee e "
                + "JOIN team t ON e.team_id = t.id "
                + "WHERE e.deleted_at IS NULL "
                + "GROUP BY t.id, t.name "
                + "ORDER BY total_compensation DESC";

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> new TeamEmployeeStatistics(
                    rs.getString("team_name"),
                    rs.getLong("employee_count"),
                    rs.getBigDecimal("total_compensation"),
                    rs.getBigDecimal("avg_compensation"),
                    rs.getInt("lowest_salary"),
                    rs.getInt("highest_salary"))
        );
    }

    /**
     * Demonstrates RIGHT JOIN by retaining every team, including teams without employees.
     */
    public String getTeamsAndEmployeesRightJoin() {
        String query = "SELECT e.name AS employee_name, t.name AS team_name "
                + "FROM employee e "
                + "RIGHT JOIN team t ON e.team_id = t.id "
                + "ORDER BY t.name, e.name";

        return jdbcTemplate.query(
            query,
            (rs, rowNum) -> formatJoinedEmployeeAndTeam(
                    rs.getString("employee_name"),
                    rs.getString("team_name"))
        ).toString();
    }

    /**
     * Resets and seeds all organization data through a POST action because the operation
     * mutates server state.
     */
    public String resetEmployeesTeamsAndProjects() {
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
        teamRepository.deleteAll();

        Team alpha = teamRepository.save(new Team("Alpha", "Seattle"));
        Team beta = teamRepository.save(new Team("Beta", "Boston"));
        teamRepository.save(new Team("Gamma", "Chicago"));

        employeeRepository.saveAll(List.of(
            new Employee("Alice", "Engineering", 120000, 15000, "555-1001", null, alpha),
            new Employee("Bob", "Engineering", 115000, 12000, null, null, alpha),
            new Employee("Carol", "Sales", 95000, 10000, "555-1003", null, beta),
            new Employee("Dave", "Sales", 90000, 8000, null, LocalDateTime.now(), beta),
            new Employee("Eve", "HR", 85000, 7000, "555-1005", null, beta),
            new Employee("Frank", "Engineering", 88000, 6000, "555-1001", null, alpha)
        ));

        projectRepository.saveAll(List.of(
            new Project("Cloud Migration", 250000, alpha),
            new Project("Mobile App", 175000, alpha),
            new Project("Sales Dashboard", 90000, beta)
        ));

        long employeeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM employee WHERE deleted_at IS NULL",
                Long.class
        );
        long teamCount = teamRepository.count();
        long projectCount = projectRepository.count();

        logger.info("Reset complete. Employees: {}, Teams: {}, Projects: {}",
                employeeCount, teamCount, projectCount);
        return "Reset complete. Employees: " + employeeCount
                + ", Teams: " + teamCount
                + ", Projects: " + projectCount;
    }

    /**
     * Replaces an employee's complete editable representation for the PUT update endpoint.
     */
    public String updateEmployee(Long employeeId, AddEmployeeRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No employee found with id " + employeeId));
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found."));

        employee.update(
                request.getName(),
                request.getDepartment(),
                request.getSalary(),
                request.getBonus(),
                request.getPhoneNumber(),
                team
        );
        employeeRepository.save(employee);

        return "Employee " + employeeId + " updated successfully.";
    }

}
