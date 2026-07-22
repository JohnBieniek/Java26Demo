package com.JohnBieniek.Java26Demo.batch;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Launches the organization demo reset batch job on the configured morning schedule. */
@Component
public class DatabaseCleanupScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleanupScheduler.class);

    private final JobOperator jobOperator;
    private final Job dailyOrganizationDemoResetJob;

    public DatabaseCleanupScheduler(JobOperator jobOperator, Job dailyOrganizationDemoResetJob) {
        this.jobOperator = jobOperator;
        this.dailyOrganizationDemoResetJob = dailyOrganizationDemoResetJob;
    }

    /** Launches a uniquely parameterized organization demo reset each morning. */
    @Scheduled(
            cron = "${app.jobs.organization-demo-reset.cron}",
            zone = "${app.jobs.organization-demo-reset.zone}")
    public void runDailyOrganizationDemoReset() {
        try {
            var parameters = new JobParametersBuilder()
                    .addLong("scheduledAt", Instant.now().toEpochMilli())
                    .toJobParameters();
            var execution = jobOperator.start(dailyOrganizationDemoResetJob, parameters);
            logger.info("Daily organization demo reset launched with execution id {}", execution.getId());
        } catch (Exception exception) {
            logger.error("Daily organization demo reset failed to launch", exception);
        }
    }
}
