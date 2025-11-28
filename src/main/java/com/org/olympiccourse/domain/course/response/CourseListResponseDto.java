package com.org.olympiccourse.domain.course.response;


import java.util.List;

public record CourseListResponseDto (
    List<CourseOverviewResponseDto> bestCourses,
    List<CourseOverviewResponseDto> courses,
    Long nextCursor,
    boolean isLast
){

}
