package com.org.olympiccourse.domain.event.service;

import com.org.olympiccourse.domain.event.dto.DetailEventResponse;
import com.org.olympiccourse.domain.event.dto.EventInfoResponse;
import com.org.olympiccourse.domain.event.dto.HomeEventResponse;
import com.org.olympiccourse.domain.event.entity.Event;
import com.org.olympiccourse.domain.event.repository.EventCustomRepository;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventCustomRepository eventCustomRepository;
    private final Clock clock;

    public HomeEventResponse getEventData(LocalDate date){
        if(date == null) date = LocalDate.now(clock);

        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<LocalDate> hasEvents = eventCustomRepository.findAllEventDatesDistinct(start, end);
        List<Event> detailEvent = eventCustomRepository.findByEventDate(date);

        List<EventInfoResponse> eventInfoResponses = detailEvent.stream()
            .map(e -> EventInfoResponse.builder()
                .place(e.getPlace().getName())
                .name(e.getName())
                .build())
            .toList();

        DetailEventResponse detailEventResponse = new DetailEventResponse(date, eventInfoResponses);
        return new HomeEventResponse(hasEvents, detailEventResponse);
    }
}
