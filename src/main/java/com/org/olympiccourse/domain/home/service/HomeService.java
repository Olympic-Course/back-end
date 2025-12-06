package com.org.olympiccourse.domain.home.service;


import com.org.olympiccourse.domain.course.response.CourseOverviewResponseDto;
import com.org.olympiccourse.domain.course.service.CourseService;
import com.org.olympiccourse.domain.event.dto.HomeEventResponse;
import com.org.olympiccourse.domain.event.service.EventService;
import com.org.olympiccourse.domain.home.dto.HomeResponse;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.weather.dto.HomeAirResponse;
import com.org.olympiccourse.domain.weather.dto.HomeWeatherResponse;
import com.org.olympiccourse.domain.weather.dto.WeatherResponse;
import com.org.olympiccourse.domain.weather.enums.Grade;
import com.org.olympiccourse.domain.weather.service.AirQualityService;
import com.org.olympiccourse.domain.weather.service.UVService;
import com.org.olympiccourse.domain.weather.service.WeatherService;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final WeatherService weatherService;
    private final AirQualityService airQualityService;
    private final UVService uvService;
    private final CourseService courseService;
    private final EventService eventService;
    private final Clock clock;

    public HomeResponse getHomeData(User user){

        WeatherResponse weatherData = weatherService.getWeather(clock);
        HomeAirResponse airQualityData = airQualityService.getAirQualityInfo();
        Grade uvGrade = uvService.getUVGrade(clock);

        HomeWeatherResponse weatherIntegrationData = HomeWeatherResponse.builder()
            .temp(weatherData.getTemp())
            .humidity(weatherData.getHumidity())
            .feelsLike(weatherData.getFeelsLike())
            .sky(weatherData.getSky())
            .pty(weatherData.getPty())
            .hourly(weatherData.getHourly())
            .findDust(airQualityData.fineDust())
            .ultrafineDust(airQualityData.ultrafineDust())
            .uv(uvGrade)
            .build();

        Long userId = (user != null) ? user.getId() : null;
        HomeEventResponse eventData = eventService.getEventData(LocalDate.now(clock));
        List<CourseOverviewResponseDto> bestCourses = courseService.getBestCourses(userId);
        return new HomeResponse(weatherIntegrationData, eventData, bestCourses);

    }
}
