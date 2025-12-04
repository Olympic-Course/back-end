package com.org.olympiccourse.domain.event.repository;

import static com.org.olympiccourse.domain.event.entity.QEvent.event;

import com.org.olympiccourse.domain.event.entity.Event;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final DatePath<LocalDate> eventDateAlias =
        Expressions.datePath(LocalDate.class, "eventDate");

    @Override
    public List<Event> findByEventDate(LocalDate date) {
        return jpaQueryFactory
            .selectDistinct(event)
            .from(event)
            .join(event.eventDates, eventDateAlias)
            .where(eventDateAlias.eq(date))
            .fetch();
    }

    @Override
    public List<LocalDate> findAllEventDatesDistinct(LocalDate start, LocalDate end) {
        return jpaQueryFactory
            .select(eventDateAlias)
            .from(event)
            .join(event.eventDates, eventDateAlias)
            .distinct()
            .where(eventDateAlias.between(start, end))
            .orderBy(eventDateAlias.asc())
            .fetch();
    }
}
