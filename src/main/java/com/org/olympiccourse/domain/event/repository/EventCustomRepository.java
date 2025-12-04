package com.org.olympiccourse.domain.event.repository;

import com.org.olympiccourse.domain.event.entity.Event;
import java.time.LocalDate;
import java.util.List;

public interface EventCustomRepository{

    List<Event> findByEventDate(LocalDate date);
    List<LocalDate> findAllEventDatesDistinct(LocalDate start, LocalDate end);
}
