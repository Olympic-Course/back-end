package com.org.olympiccourse.domain.usercourse.request;

import java.util.List;

public record CreateCourseMemoRequestDto(
    List<MemoRequestDto> steps
) {

}
