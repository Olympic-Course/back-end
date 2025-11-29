package com.org.olympiccourse.domain.tag.repository;

import com.org.olympiccourse.domain.tag.response.CourseTagProjection;
import java.util.List;

public interface CourseTagCustomRepository {

    List<CourseTagProjection> findByCourseIds(List<Long> courseIds);
}
