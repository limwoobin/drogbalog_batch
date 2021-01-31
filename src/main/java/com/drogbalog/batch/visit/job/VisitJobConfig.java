package com.drogbalog.batch.visit.job;

import com.drogbalog.batch.global.job.CreateDateJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class VisitJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final VisitorTasklet visitorTasklet;
//    private final CreateDateJobParameter jobParameter;

//    @Bean
//    @JobScope
//    public CreateDateJobParameter jobParameter(@Value("#{jobParameters[createDate]}") String createDate) {
//        return new CreateDateJobParameter(createDate);
//    }

    @Bean
    public Job visitorJob() {
        return jobBuilderFactory.get(VisitCode.VISITOR_JOB.getName())
                .start(visitorJobStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step visitorJobStep(@Value("#{jobParameters[createDate]}") String createDate) {
        log.info(">>>>> Job Parameters {}" , createDate);

        LocalDate localDate = LocalDate.parse(createDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return stepBuilderFactory.get(VisitCode.VISITOR_JOB_STEP.getName())
                .tasklet(visitorTasklet)
                .build();
    }

}
