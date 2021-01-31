package com.drogbalog.batch.visit.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisitorCount {
    private long today;
    private long fullDate;

    public void update(long today , long fullDate) {
        this.today = today;
        this.fullDate = fullDate;
    }
}
