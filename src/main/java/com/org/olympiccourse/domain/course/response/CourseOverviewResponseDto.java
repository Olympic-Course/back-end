package com.org.olympiccourse.domain.course.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CourseOverviewResponseDto {

    Long courseId;

    String thumbnail;

    String title;

    String writer;

    Long likeNum;

    Boolean liked;
}
