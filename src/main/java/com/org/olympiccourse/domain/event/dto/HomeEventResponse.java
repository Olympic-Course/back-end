package com.org.olympiccourse.domain.event.dto;

import java.time.LocalDate;
import java.util.List;

public record HomeEventResponse (
    List<LocalDate> eventDates,
    DetailEventResponse detailEvent
){
}
