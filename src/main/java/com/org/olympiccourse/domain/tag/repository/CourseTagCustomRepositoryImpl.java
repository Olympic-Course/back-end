package com.org.olympiccourse.domain.tag.repository;

import static com.org.olympiccourse.domain.course.entity.QCourse.course;
import static com.org.olympiccourse.domain.tag.entity.QCourseTag.courseTag;

import com.org.olympiccourse.domain.tag.response.CourseTagProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseTagCustomRepositoryImpl implements CourseTagCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CourseTagProjection> findByCourseIds(List<Long> courseIds) {
        return jpaQueryFactory.select(Projections.constructor(CourseTagProjection.class,
                course.id, courseTag.tag))
            .from(courseTag)
            .join(courseTag.course, course)
            .where(course.id.in(courseIds))
            .fetch();
    }
}
