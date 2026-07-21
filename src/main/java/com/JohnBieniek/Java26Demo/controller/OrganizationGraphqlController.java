package com.JohnBieniek.Java26Demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.JohnBieniek.Java26Demo.graphql.GraphqlInputs;
import com.JohnBieniek.Java26Demo.model.organization.Employee;
import com.JohnBieniek.Java26Demo.model.organization.Project;
import com.JohnBieniek.Java26Demo.model.organization.Team;
import com.JohnBieniek.Java26Demo.repository.EmployeeRepository;
import com.JohnBieniek.Java26Demo.repository.ProjectRepository;
import com.JohnBieniek.Java26Demo.repository.TeamRepository;
import com.JohnBieniek.Java26Demo.service.SqlService;

import jakarta.validation.Valid;

@Controller
@Validated
public class OrganizationGraphqlController {
    private final TeamRepository teams;
    private final EmployeeRepository employees;
    private final ProjectRepository projects;
    private final SqlService sqlService;

    public OrganizationGraphqlController(TeamRepository teams, EmployeeRepository employees,
            ProjectRepository projects, SqlService sqlService) {
        this.teams = teams;
        this.employees = employees;
        this.projects = projects;
        this.sqlService = sqlService;
    }

    @QueryMapping
    public List<Team> teams() {
        return teams.findAll();
    }

    @QueryMapping
    public Team team(@Argument Long id) {
        return teams.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Employee> employees() {
        return employees.findByDeletedAtIsNullOrderByNameAsc();
    }

    @QueryMapping
    public Employee employee(@Argument Long id) {
        return employees.findById(id).filter(value -> value.getDeletedAt() == null).orElse(null);
    }

    @QueryMapping
    public List<Project> projects() {
        return projects.findAll();
    }

    @BatchMapping(typeName = "Team", field = "employees")
    public Map<Team, List<Employee>> teamEmployees(List<Team> sourceTeams) {
        var byTeamId = employees.findByTeamIdInAndDeletedAtIsNullOrderByNameAsc(
                sourceTeams.stream().map(Team::getId).toList()).stream()
                .collect(Collectors.groupingBy(employee -> employee.getTeam().getId()));
        return sourceTeams.stream().collect(Collectors.toMap(
                team -> team, team -> byTeamId.getOrDefault(team.getId(), List.of())));
    }

    @BatchMapping(typeName = "Team", field = "projects")
    public Map<Team, List<Project>> teamProjects(List<Team> sourceTeams) {
        var byTeamId = projects.findByTeamIdInOrderByNameAsc(sourceTeams.stream().map(Team::getId).toList()).stream()
                .collect(Collectors.groupingBy(project -> project.getTeam().getId()));
        return sourceTeams.stream().collect(Collectors.toMap(
                team -> team, team -> byTeamId.getOrDefault(team.getId(), List.of())));
    }

    @SchemaMapping(typeName = "Employee")
    public int totalCompensation(Employee employee) {
        return employee.getSalary() + employee.getBonus();
    }

    @MutationMapping
    public Team createTeam(@Argument @Valid GraphqlInputs.CreateTeam input) {
        return teams.save(new Team(input.name(), input.location()));
    }

    @MutationMapping
    public Employee createEmployee(@Argument @Valid GraphqlInputs.CreateEmployee input) {
        Team team = requireTeam(input.teamId());
        return employees.save(new Employee(input.name(), input.department(), input.salary(), input.bonus(),
                input.phoneNumber(), null, team));
    }

    @MutationMapping
    public Project createProject(@Argument @Valid GraphqlInputs.CreateProject input) {
        return projects.save(new Project(input.name(), input.budget(), requireTeam(input.teamId())));
    }

    @MutationMapping
    @Transactional
    public boolean deleteEmployee(@Argument Long id) {
        Employee employee = employees.findById(id).orElse(null);
        if (employee == null || employee.getDeletedAt() != null) {
            return false;
        }
        employee.softDelete();
        employees.save(employee);
        return true;
    }

    @MutationMapping
    public String resetOrganizationDemo() {
        return sqlService.resetEmployeesTeamsAndProjects();
    }

    private Team requireTeam(Long id) {
        return teams.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Team " + id + " was not found"));
    }

}
