package com.org.olympiccourse.domain.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.olympiccourse.domain.weather.enums.Pty;
import com.org.olympiccourse.domain.weather.enums.Sky;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class HomeHourlyWeatherResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    private Double temp;
    private Sky sky;
    private Pty pty;

    public HomeHourlyWeatherResponse(String time, Double temp, Sky sky, Pty pty) {
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
        this.temp = temp;
        this.sky = sky;
        this.pty = pty;
    }
}
