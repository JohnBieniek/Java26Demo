package com.JohnBieniek.Java26Demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.JohnBieniek.Java26Demo.dto.AddEmployeeRequest;
import com.JohnBieniek.Java26Demo.model.Employee;
import com.JohnBieniek.Java26Demo.model.Team;
import com.JohnBieniek.Java26Demo.repository.EmployeeRepository;
import com.JohnBieniek.Java26Demo.repository.TeamRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping(value = "/demo")
public class SqlController {
    private static final Logger logger = LoggerFactory.getLogger(SqlController.class);

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final JdbcTemplate jdbcTemplate;

    public SqlController(EmployeeRepository employeeRepository,
                         TeamRepository teamRepository,
                         JdbcTemplate jdbcTemplate) {
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/resetTeamsAndEmployees")
    public String resetTeamsAndEmployees() {
        employeeRepository.deleteAll();
        teamRepository.deleteAll();

        Team alpha = teamRepository.save(new Team("Alpha", "Seattle"));
        Team beta = teamRepository.save(new Team("Beta", "Boston"));

        employeeRepository.saveAll(List.of(
            new Employee("Alice", "Engineering", 120000, 15000, "555-1001", null, alpha),
            new Employee("Bob", "Engineering", 115000, 12000, null, null, alpha),
            new Employee("Carol", "Sales", 95000, 10000, "555-1003", null, beta),
            new Employee("Dave", "Sales", 90000, 8000, null, LocalDateTime.now(), beta),
            new Employee("Eve", "HR", 85000, 7000, "555-1005", null, beta)
        ));

        long employeeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM employee WHERE deleted_at IS NULL",
                Long.class
        );
        long teamCount = teamRepository.count();

        logger.info("Reset complete. Employees: {}, Teams: {}", employeeCount, teamCount);
        return "Reset complete. Employees: " + employeeCount + ", Teams: " + teamCount;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getHighlyCompensatedEmployees")
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
        if(rows.isEmpty()) {
            return "No highly compensated employees found. Run the reset endpoint to populate the database or add more employees with higher compensation.";
        }
        return rows.toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getDeletedEmployees")
    public String getDeletedEmployees() {
        String query = "SELECT e.name, e.deleted_at "
                + "FROM employee e "
                + "WHERE e.deleted_at IS NOT NULL";

        List<String> rows = jdbcTemplate.query(
            query,
            (rs, rowNum) -> rs.getString("name") + " | deleted at " + rs.getTimestamp("deleted_at")
        );

        logger.info("Deleted employee query result: {}", rows);
        return rows.toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEmployeePhoneNumbers")
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

    @RequestMapping(method = RequestMethod.GET, path = "/addTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTeam(@RequestParam @NotBlank String name, @RequestParam @NotBlank String location) {
        Team team = teamRepository.save(new Team(name, location));
        return "Team added successfully with id " + team.getId() + ".";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    public String addEmployee(@Valid @ModelAttribute AddEmployeeRequest request) {
        teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found."));

        String sql = "INSERT INTO employee (name, department, salary, bonus, phone_number, deleted_at, team_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
            sql,
            request.getName(),
            request.getDepartment(),
            request.getSalary(),
            request.getBonus(),
            request.getPhoneNumber(),
            null,
            request.getTeamId()
        );

        return "Employee added successfully.";
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteEmployee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@RequestParam @NotNull Long employeeId) {
        String sql = "UPDATE employee SET deleted_at = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, LocalDateTime.now(), employeeId);

        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found with id " + employeeId);
        }
    }
}
