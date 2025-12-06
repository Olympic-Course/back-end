package com.org.olympiccourse.domain.weather.dto;

import com.org.olympiccourse.domain.weather.enums.Pty;
import com.org.olympiccourse.domain.weather.enums.Sky;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherResponse {

    private Double temp;
    private Double feelsLike;
    private Integer humidity;
    private Sky sky;
    private Pty pty;
    private List<HomeHourlyWeatherResponse> hourly;
}
