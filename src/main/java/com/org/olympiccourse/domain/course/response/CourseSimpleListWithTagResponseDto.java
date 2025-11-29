package com.org.olympiccourse.domain.course.response;

import java.util.List;

public record CourseSimpleListWithTagResponseDto (

    List<CourseOverviewTagResponseDto> courses,
    Long nextCursor,
    boolean isLast
){

}
