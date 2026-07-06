package com.JohnBieniek.Java26Demo.batch;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.JohnBieniek.Java26Demo.model.organization.Employee;
import com.JohnBieniek.Java26Demo.model.organization.Project;
import com.JohnBieniek.Java26Demo.model.organization.Team;
import com.JohnBieniek.Java26Demo.repository.EmployeeRepository;
import com.JohnBieniek.Java26Demo.repository.ProjectRepository;
import com.JohnBieniek.Java26Demo.repository.TeamRepository;

@SpringBootTest
class DatabaseCleanupBatchTests {
    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private Job dailyDatabaseCleanupJob;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void cleanupJobDeletesOrganizationRows() throws Exception {
        Team team = teamRepository.save(new Team("Cleanup Test", "Test Location"));
        employeeRepository.save(new Employee(
                "Cleanup Employee", "Test", 1, 0, "555-0100", null, team));
        projectRepository.save(new Project("Cleanup Project", 1, team));

        var parameters = new JobParametersBuilder()
                .addLong("testRun", Instant.now().toEpochMilli())
                .toJobParameters();
        var execution = jobOperator.start(dailyDatabaseCleanupJob, parameters);

        assertThat(execution.getStatus().isUnsuccessful()).isFalse();
        assertThat(projectRepository.count()).isZero();
        assertThat(employeeRepository.count()).isZero();
        assertThat(teamRepository.count()).isZero();
    }
}
