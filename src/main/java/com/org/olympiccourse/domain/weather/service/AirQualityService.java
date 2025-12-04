package com.org.olympiccourse.domain.weather.service;

import com.org.olympiccourse.domain.weather.dto.AirApiResponseWrapper;
import com.org.olympiccourse.domain.weather.dto.AirApiResponseWrapper.Row;
import com.org.olympiccourse.domain.weather.dto.HomeAirResponse;
import com.org.olympiccourse.domain.weather.enums.Grade;
import com.org.olympiccourse.domain.weather.util.AirUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirQualityService {
    private final RestClient restClient;
    @Value("${openapi.air.url}")
    private String URL;

    public HomeAirResponse getAirQualityInfo(){
        AirApiResponseWrapper response = restClient
            .get()
            .uri(URL)
            .retrieve()
            .body(AirApiResponseWrapper.class);

        Row row = response.getListAirQualityByDistrictService().getRow().get(0);
        Grade PM = AirUtils.getPMGrade(row.getPm());
        Grade FPM = AirUtils.getFPMGrade(row.getFpm());

        return new HomeAirResponse(PM, FPM);
    }
}
