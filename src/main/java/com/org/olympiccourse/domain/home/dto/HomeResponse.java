package com.org.olympiccourse.domain.home.dto;

import com.org.olympiccourse.domain.course.response.CourseOverviewResponseDto;
import com.org.olympiccourse.domain.event.dto.HomeEventResponse;
import com.org.olympiccourse.domain.weather.dto.HomeWeatherResponse;
import java.util.List;

public record HomeResponse(
    HomeWeatherResponse weather,
    HomeEventResponse event,
    List<CourseOverviewResponseDto> bestCourses
) {

}
