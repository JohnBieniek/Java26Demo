package com.JohnBieniek.Java26Demo.batch;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.JohnBieniek.Java26Demo.repository.EmployeeRepository;
import com.JohnBieniek.Java26Demo.repository.ProjectRepository;
import com.JohnBieniek.Java26Demo.repository.TeamRepository;

/** Configures the Spring Batch job that removes all organization demo data. */
@Configuration
public class DatabaseCleanupBatchConfiguration {
    /**
     * Defines the complete daily cleanup job.
     *
     * @param jobRepository Spring Batch execution repository
     * @param wipeOrganizationDataStep transactional cleanup step
     * @return configured cleanup job
     */
    @Bean
    public Job dailyDatabaseCleanupJob(
            JobRepository jobRepository,
            Step wipeOrganizationDataStep) {
        return new JobBuilder("dailyDatabaseCleanupJob", jobRepository)
                .start(wipeOrganizationDataStep)
                .build();
    }

    /**
     * Deletes application rows in foreign-key-safe order inside one transaction.
     *
     * @param jobRepository Spring Batch execution repository
     * @param transactionManager transaction manager used by the tasklet step
     * @param projectRepository project persistence access
     * @param employeeRepository employee persistence access
     * @param teamRepository team persistence access
     * @return cleanup tasklet step
     */
    @Bean
    public Step wipeOrganizationDataStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            TeamRepository teamRepository) {
        return new StepBuilder("wipeOrganizationDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    projectRepository.deleteAllInBatch();
                    employeeRepository.deleteAllInBatch();
                    teamRepository.deleteAllInBatch();
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
