package com.drogbalog.batch.visit.job;

import com.drogbalog.batch.global.job.CreateDateJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class VisitJobConfig {
    public static final String JOB_NAME = "visitorJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final VisitorTasklet visitorTasklet;

    @Bean(JOB_NAME + "jobParameter")
    @JobScope
    public CreateDateJobParameter jobParameter() {
        return new CreateDateJobParameter();
    }

    @Bean(name = JOB_NAME)
    public Job visitorJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(visitorJobStep())
                .preventRestart()
                .build();
    }

    @Bean(name = JOB_NAME + "_step")
    @JobScope
    public Step visitorJobStep() {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .tasklet(visitorTasklet)
                .build();
    }

}
