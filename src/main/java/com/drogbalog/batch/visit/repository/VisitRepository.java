package com.drogbalog.batch.visit.repository;

import com.drogbalog.batch.global.jpa.DefaultRepository;
import com.drogbalog.batch.visit.domain.entity.Visit;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VisitRepository extends DefaultRepository<Visit, Long> {
    Visit findByDate(LocalDate date);
}
