package com.org.olympiccourse.domain.course.request;

import com.org.olympiccourse.domain.tag.entity.Tag;
import java.util.List;

public record CourseSearchCond(
    String keyword,
    List<Tag> tags,
    Long cursor
    ) {

}
