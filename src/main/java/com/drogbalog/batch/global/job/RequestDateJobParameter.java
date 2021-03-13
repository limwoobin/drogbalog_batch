package com.drogbalog.batch.global.job;

import com.drogbalog.batch.global.utils.DateTimeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;

@Log4j2
@Getter
@NoArgsConstructor
public class RequestDateJobParameter {
    public static final String REQUEST_DATE_PARAM = "requestDate";
    private LocalDate requestDate;

    @Value("#{jobParameters[requestDate]}")
    public void setRequestDate(String requestDate) {
        this.requestDate = DateTimeUtil.toLocalDate(requestDate);
    }
}
