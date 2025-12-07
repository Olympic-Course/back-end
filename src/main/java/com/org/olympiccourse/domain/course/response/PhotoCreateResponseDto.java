package com.org.olympiccourse.domain.course.response;

import lombok.Builder;

@Builder
public record PhotoCreateResponseDto(

    Long photoId,

    String path,

    Boolean isRep) {
}
