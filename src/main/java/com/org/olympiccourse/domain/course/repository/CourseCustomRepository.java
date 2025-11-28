package com.org.olympiccourse.domain.course.repository;

import com.org.olympiccourse.domain.course.request.CourseSearchCond;
import com.org.olympiccourse.domain.course.request.MyCourseVisibility;
import com.org.olympiccourse.domain.course.response.CourseOverviewResponseDto;
import java.util.List;

public interface CourseCustomRepository {

    List<CourseOverviewResponseDto> findBestThreeCourses(Long userId);

    List<CourseOverviewResponseDto> searchCourseList(Long id, CourseSearchCond condition, int size);

    List<CourseOverviewResponseDto> findByUserIdWithSearchCond(Long userId,
        CourseSearchCond condition, MyCourseVisibility visibility, int size);
}
