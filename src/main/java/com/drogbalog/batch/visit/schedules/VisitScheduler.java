package com.drogbalog.batch.visit.schedules;

import com.drogbalog.batch.global.job.RequestDateJobParameter;
import com.drogbalog.batch.global.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Log4j2
public class VisitScheduler {
    private final Job job;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "${cron.visit}")
    public void runVisitorJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("Visit Scheduler >>>>> {}" , DateTimeUtil.localDateToString(LocalDate.now()));
        jobLauncher.run(job ,
                new JobParametersBuilder().addString(
                        RequestDateJobParameter.REQUEST_DATE_PARAM ,
                        DateTimeUtil.localDateToString(LocalDate.now())).toJobParameters());
    }
}
