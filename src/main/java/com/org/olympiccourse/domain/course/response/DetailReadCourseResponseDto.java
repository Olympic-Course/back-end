package com.org.olympiccourse.domain.course.response;

import com.org.olympiccourse.domain.course.entity.Cost;
import com.org.olympiccourse.domain.course.entity.Duration;
import com.org.olympiccourse.domain.tag.entity.Tag;
import java.util.List;
import lombok.Builder;

@Builder
public record DetailReadCourseResponseDto(
    Long courseId,

    String title,

    String writer,

    Boolean secret,

    List<Tag> tag,

    String comment,

    List<StepResponseDto> steps,

    Duration duration,

    Cost cost,

    Boolean liked,

    Long likeNum
) {

}
