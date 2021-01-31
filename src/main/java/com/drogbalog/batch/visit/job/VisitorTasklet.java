package com.drogbalog.batch.visit.job;

import com.drogbalog.batch.visit.domain.vo.VisitorCount;
import com.drogbalog.batch.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Component
@StepScope
public class VisitorTasklet implements Tasklet , StepExecutionListener {
    private final RedisTemplate<String , Object> redisTemplate;
    private final VisitService visitService;
    private VisitorCount visitorCount = new VisitorCount();

    @Value("#{jobParameters[createDate]}")
    private LocalDate createDate;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String today = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(VisitCode.TODAY.getName())));
        String fullDate = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(VisitCode.FULL_DATE.getName())));
        log.info(">>>>>>>>>>> TODAY {}" , createDate + ": " + today);
        log.info(">>>>>>>>>>> FULL_DATE {}" , fullDate);
        visitorCount.update(Long.parseLong(today) , Long.parseLong(fullDate));
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String todayNumberOfVisitors = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(VisitCode.TODAY.getName())));
        redisTemplate.opsForValue().increment(VisitCode.FULL_DATE.getName() , Long.parseLong(todayNumberOfVisitors));
        visitService.visitorUpdate(visitorCount , createDate);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        redisTemplate.opsForValue().set(VisitCode.TODAY.getName(), 0);
        return ExitStatus.COMPLETED;
    }
}
