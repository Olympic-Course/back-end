package com.org.olympiccourse.domain.weather.service;

import com.org.olympiccourse.domain.weather.enums.Grade;
import com.org.olympiccourse.domain.weather.util.UVUtils;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class UVService {
    private final RestClient restClient;
    @Value("${openapi.weather.service-key}")
    private String serviceKey;

    public Grade getUVGrade(Clock clock){
        String result = restClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("apihub.kma.go.kr")
                .path("/api/typ01/url/kma_sfctm_uv.php")
                .queryParam("tm", getDateTime(clock))
                .queryParam("stn", 108)
                .queryParam("help", 0)
                .queryParam("authKey", serviceKey)
                .build())
            .retrieve()
            .body(String.class);

        Grade uvGrade = Grade.UNKNOWN;
        for(String line : result.split("\n")){
            if (!line.matches("^\\d{12}.*")) continue;

            String[] cols = line.trim().split("\\s+");
            double uvB = Double.parseDouble(cols[5]);
            uvGrade = UVUtils.getUVGrade(uvB);
        }
        return uvGrade;
    }

    private String getDateTime(Clock clock){
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime base = now.minusMinutes(5);
        int minute = base.getMinute();
        int floored = (minute/10) * 10;

        base = base.withMinute(floored);
        return base.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

}
