package com.org.olympiccourse.domain.tag.response;

import com.org.olympiccourse.domain.tag.entity.Tag;

public record CourseTagProjection(
    Long courseId,
    Tag tag
) {

}
