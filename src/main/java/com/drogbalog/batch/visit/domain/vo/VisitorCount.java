package com.drogbalog.batch.visit.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisitorCount {
    private long today;
    private long fullDate;

    public void reset() {
        this.today = 0;
        this.fullDate = 0;
    }

    public void update(long today , long fullDate) {
        this.today = today;
        this.fullDate = fullDate;
    }
}
