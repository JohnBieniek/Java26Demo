package com.JohnBieniek.Java26Demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.dto.AddEmployeeRequest;
import com.JohnBieniek.Java26Demo.manager.SqlManager;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping(value = "/demo")
public class SqlController {
    private final SqlManager sqlManager;

    public SqlController(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    public String addEmployee(@Valid @ModelAttribute AddEmployeeRequest request) {
        return sqlManager.addEmployee(request);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addProject")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Add a project",
        description = "Creates a project and assigns it to an existing team."
    )
    public String addProject(
            @RequestParam @NotBlank String name,
            @RequestParam @Min(0) int budget,
            @RequestParam @NotNull Long teamId) {
        return sqlManager.addProject(name, budget, teamId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTeam(@RequestParam @NotBlank String name, @RequestParam @NotBlank String location) {
        return sqlManager.addTeam(name, location);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteEmployee")
    @Operation(description = "SQL injection is prevented by using parameterized queries with JdbcTemplate." +
            " The employee is not actually deleted from the database, but marked as deleted by setting the deleted_at timestamp. " +
            "This allows us to maintain data integrity and auditability while effectively removing the employee from active queries.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@RequestParam @NotNull Long employeeId) {
        sqlManager.deleteEmployee(employeeId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAllEmployeesAndTeamsUnionAll")
    @Operation(
        summary = "Use UNION ALL to include unmatched employees and teams",
        description = "The first SELECT uses LEFT JOIN to return every employee and any matching team. "
                + "The second SELECT uses RIGHT JOIN with WHERE e.id IS NULL to return only teams "
                + "that have no employees. UNION ALL combines these two result sets without repeating "
                + "the employee/team matches already returned by the first SELECT."
    )
    public String getAllEmployeesAndTeamsUnionAll() {
        return sqlManager.getAllEmployeesAndTeamsUnionAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getDeletedEmployees")
    public String getDeletedEmployees() {
        return sqlManager.getDeletedEmployees();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getDuplicateEmployeePhoneNumbers")
    @Operation(
        summary = "Detect duplicate employee phone numbers",
        description = "ROW_NUMBER partitions active employees by phone number and orders each group by id. "
                + "The outer query returns rows numbered greater than one, identifying duplicate records "
                + "while retaining the first record in each group as the original."
    )
    public String getDuplicateEmployeePhoneNumbers() {
        return sqlManager.getDuplicateEmployeePhoneNumbers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEmployeePhoneNumbers")
    public String getEmployeePhoneNumbers() {
        return sqlManager.getEmployeePhoneNumbers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEmployeesTeamsAndProjects")
    @Operation(
        summary = "Join employee, team, and project tables",
        description = "Joins employees to their team, then joins each team to its projects. "
                + "Only active employees with a matching team and project are returned."
    )
    public String getEmployeesTeamsAndProjects() {
        return sqlManager.getEmployeesTeamsAndProjects();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEmployeesWithTeamsInnerJoin")
    @Operation(
        summary = "Demonstrate SQL INNER JOIN where info is only returned if there is a match in both tables",
        description = "Returns only employees that have a matching team."
    )
    public String getEmployeesWithTeamsInnerJoin() {
        return sqlManager.getEmployeesWithTeamsInnerJoin();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getHighlyCompensatedEmployees")
    @Operation(
        summary = "Demonstrate SQL WHERE and HAVING",
        description = "WHERE filters individual employee rows before grouping. "
                + "HAVING filters grouped results after SUM is calculated."
    )
    public String getHighlyCompensatedEmployees() {
        return sqlManager.getHighlyCompensatedEmployees();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getProjects")
    @Operation(
        summary = "Get all projects",
        description = "Returns every project with its budget and assigned team."
    )
    public String getProjects() {
        return sqlManager.getProjects();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getTeamEmployeeStatistics")
    @Operation(description = "Demonstrate the use of COUNT, SUM, AVG, MIN, MAX, GROUP BY, and JOIN in SQL.")
    public String getTeamEmployeeStatistics() {
        return sqlManager.getTeamEmployeeStatistics();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getTeamsAndEmployeesRightJoin")
    @Operation(
        summary = "Demonstrate SQL RIGHT JOIN returns all rows from the right table (teams) and matched rows from the left table (employees)",
        description = "Returns every team, including teams without employees. "
                + "Employee data is null when no employee matches the team."
    )
    public String getTeamsAndEmployeesRightJoin() {
        return sqlManager.getTeamsAndEmployeesRightJoin();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/resetEmployeesTeamsAndProjects")
    public String resetEmployeesTeamsAndProjects() {
        return sqlManager.resetEmployeesTeamsAndProjects();
    }
}
