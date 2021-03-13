package com.drogbalog.batch.visit.job;

import com.drogbalog.batch.global.job.RequestDateJobParameter;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Component
@StepScope
public class VisitorTasklet implements Tasklet , StepExecutionListener {
    public static final String NUMBER_OF_VISITORS_TODAY = "today";
    public static final String NUMBER_OF_VISITORS_FULL_DATE = "fullDate";

    private final RequestDateJobParameter jobParameter;
    private final RedisTemplate<String , Object> redisTemplate;
    private final VisitService visitService;
    private final VisitorCount visitorCount = new VisitorCount();



    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Tasklet Before >>> {}" , jobParameter.getRequestDate());
        String today = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(NUMBER_OF_VISITORS_TODAY)));
        String fullDate = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(NUMBER_OF_VISITORS_FULL_DATE)));
        log.info(">>>>>>>>>>> TODAY {}" , jobParameter.getRequestDate() + ": " + today);
        log.info(">>>>>>>>>>> FULL_DATE {}" , fullDate);
        visitorCount.update(Long.parseLong(today) , Long.parseLong(fullDate));
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String todayNumberOfVisitors = String.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(NUMBER_OF_VISITORS_TODAY)));
        redisTemplate.opsForValue().increment(NUMBER_OF_VISITORS_FULL_DATE , Long.parseLong(todayNumberOfVisitors));
        visitService.visitorUpdate(visitorCount , jobParameter.getRequestDate());
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Tasklet After >>> {}" , jobParameter.getRequestDate());
        redisTemplate.opsForValue().set(NUMBER_OF_VISITORS_TODAY , 0);
        return ExitStatus.COMPLETED;
    }
}
