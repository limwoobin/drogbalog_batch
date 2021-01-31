package com.drogbalog.batch.global.job;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@Getter
public class CreateDateJobParameter {
    private LocalDate createDate;

    public CreateDateJobParameter(String createDate) {
        this.createDate = LocalDate.parse(createDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
