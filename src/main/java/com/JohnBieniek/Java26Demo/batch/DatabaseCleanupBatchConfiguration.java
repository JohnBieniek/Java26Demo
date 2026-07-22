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

import com.JohnBieniek.Java26Demo.service.SqlService;

/** Configures the Spring Batch job that resets the organization demo data. */
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
    public Job dailyOrganizationDemoResetJob(
            JobRepository jobRepository,
            Step resetOrganizationDemoStep) {
        return new JobBuilder("dailyOrganizationDemoResetJob", jobRepository)
                .start(resetOrganizationDemoStep)
                .build();
    }

    /**
     * Runs the same reset operation exposed by the resetOrganizationDemo mutation.
     *
     * @param jobRepository Spring Batch execution repository
     * @param transactionManager transaction manager used by the tasklet step
     * @param sqlService organization demo reset service
     * @return reset tasklet step
     */
    @Bean
    public Step resetOrganizationDemoStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            SqlService sqlService) {
        return new StepBuilder("resetOrganizationDemoStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    sqlService.resetEmployeesTeamsAndProjects();
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
