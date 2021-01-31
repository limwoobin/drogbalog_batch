package com.drogbalog.batch.visit.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VisitCode {
    VISITOR_JOB("visitorJob"),
    VISITOR_JOB_STEP("visitorJobStep"),
    TODAY("today"),
    FULL_DATE("fullDate");

    private final String name;
}
