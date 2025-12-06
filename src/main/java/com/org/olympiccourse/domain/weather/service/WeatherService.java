package com.org.olympiccourse.domain.weather.service;

import com.org.olympiccourse.domain.weather.dto.HomeHourlyWeatherResponse;
import com.org.olympiccourse.domain.weather.dto.HomeWeatherResponse;
import com.org.olympiccourse.domain.weather.dto.WeatherApiResponseWrapper;
import com.org.olympiccourse.domain.weather.dto.WeatherApiResponseWrapper.Item;
import com.org.olympiccourse.domain.weather.dto.WeatherResponse;
import com.org.olympiccourse.domain.weather.enums.Pty;
import com.org.olympiccourse.domain.weather.enums.Sky;
import com.org.olympiccourse.domain.weather.util.WeatherUtils;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final RestClient restClient;
    @Value("${openapi.weather.service-key}")
    private String SERVICE_KEY;

    public WeatherResponse getWeather(Clock clock) {
        WeatherApiResponseWrapper response = restClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("apihub.kma.go.kr")
                .path("/api/typ02/openApi/VilageFcstInfoService_2.0/getUltraSrtFcst")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 100)
                .queryParam("dataType", "json")
                .queryParam("base_date", getBaseDate(clock))
                .queryParam("base_time", getBaseTime(clock))
                .queryParam("nx", 62)
                .queryParam("ny", 126)
                .queryParam("authKey", SERVICE_KEY).build())
            .retrieve()
            .body(WeatherApiResponseWrapper.class);

        List<Item> weatherApiResponses = response.getResponse().getBody().getItems().getItem();

        Map<String, List<Item>> grouped = weatherApiResponses.stream()
            .collect(Collectors.groupingBy(Item::getFcstTime));

        List<String> sortedTimes = grouped.keySet().stream()
            .sorted()
            .limit(6)
            .toList();
        List<HomeHourlyWeatherResponse> hourly = new ArrayList<>();

        for (String time : sortedTimes) {
            List<Item> list = grouped.get(time);

            Double temp = Double.valueOf(findValue(list, "T1H"));
            Sky sky = Sky.getSky(findValue(list, "SKY"));
            Pty pty = Pty.getPty(findValue(list, "PTY"));

            hourly.add(new HomeHourlyWeatherResponse(time, temp, sky, pty));
        }

        String firstTime = sortedTimes.get(0);
        List<Item> first = grouped.get(firstTime);

        double nowTemp = Double.valueOf(findValue(first, "T1H"));
        int humidity = Integer.valueOf(findValue(first, "REH"));
        double feelsLikeTemp;

        int month = LocalDateTime.now(clock).getMonthValue(); // 월

        if (month >= 5 && month <= 9) { // 여름 (5~9월)
            double nowHumidity = Double.valueOf(findValue(first, "REH"));
            feelsLikeTemp = WeatherUtils.calculateSummerFeelsLike(nowTemp, nowHumidity);
        } else { // 겨울 체감온도
            double nowWindSpeed = Double.valueOf(findValue(first, "WSD")); // 풍속: WSD
            feelsLikeTemp = WeatherUtils.calculateWinterFeelsLike(nowTemp, nowWindSpeed);
        }

        return WeatherResponse.builder()
            .temp(nowTemp)
            .feelsLike(feelsLikeTemp)
            .humidity(humidity)
            .sky(Sky.getSky(findValue(first, "SKY")))
            .pty(Pty.getPty(findValue(first, "PTY")))
            .hourly(hourly)
            .build();
    }

    private String findValue(List<Item> list, String category) {
        return list.stream()
            .filter(i -> i.getCategory().equals(category))
            .findFirst()
            .map(Item::getFcstValue)
            .orElse("0");
    }

    private String getBaseDate(Clock clock) {
        LocalDateTime base = LocalDateTime.now(clock).minusMinutes(45);
        log.info("baseDate: {}", base);
        return base.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String getBaseTime(Clock clock) {
        LocalDateTime base = LocalDateTime.now(clock).minusMinutes(45)
            .withMinute(30).withSecond(0).withNano(0);
        log.info("baseTime: {}", base);
        return base.format(DateTimeFormatter.ofPattern("HHmm"));
    }
}


