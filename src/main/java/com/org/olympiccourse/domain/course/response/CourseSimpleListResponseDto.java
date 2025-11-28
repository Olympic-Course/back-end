package com.org.olympiccourse.domain.course.response;

import java.util.List;


public record CourseSimpleListResponseDto (

    List<CourseOverviewResponseDto> courses,
    Long nextCursor,
    boolean isLast
){

}
