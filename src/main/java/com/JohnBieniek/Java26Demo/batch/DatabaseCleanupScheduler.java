package com.JohnBieniek.Java26Demo.batch;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Launches the database cleanup batch job on the configured morning schedule. */
@Component
public class DatabaseCleanupScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleanupScheduler.class);

    private final JobOperator jobOperator;
    private final Job dailyDatabaseCleanupJob;

    public DatabaseCleanupScheduler(JobOperator jobOperator, Job dailyDatabaseCleanupJob) {
        this.jobOperator = jobOperator;
        this.dailyDatabaseCleanupJob = dailyDatabaseCleanupJob;
    }

    /** Launches a uniquely parameterized cleanup execution each morning. */
    @Scheduled(
            cron = "${app.jobs.database-cleanup.cron}",
            zone = "${app.jobs.database-cleanup.zone}")
    public void runDailyDatabaseCleanup() {
        try {
            var parameters = new JobParametersBuilder()
                    .addLong("scheduledAt", Instant.now().toEpochMilli())
                    .toJobParameters();
            var execution = jobOperator.start(dailyDatabaseCleanupJob, parameters);
            logger.info("Daily database cleanup launched with execution id {}", execution.getId());
        } catch (Exception exception) {
            logger.error("Daily database cleanup failed to launch", exception);
        }
    }
}
