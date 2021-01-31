package com.drogbalog.batch.visit.service;

import com.drogbalog.batch.visit.domain.entity.Visit;
import com.drogbalog.batch.visit.domain.vo.VisitorCount;
import com.drogbalog.batch.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Log4j2
@RequiredArgsConstructor
@Service
public class VisitService {
    private final VisitRepository visitRepository;

    @Transactional
    public void visitorUpdate(VisitorCount visitorCount , LocalDate date) {
        Visit todayVisit = Visit.builder()
                .count(visitorCount.getToday())
                .date(date)
                .build();

        visitRepository.save(todayVisit);
    }
}
