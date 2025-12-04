package com.org.olympiccourse.domain.weather.dto;

import com.org.olympiccourse.domain.weather.enums.Grade;

public record HomeAirResponse(
    Grade fineDust,
    Grade ultrafineDust) {
}
