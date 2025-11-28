package com.org.olympiccourse.domain.usercourse.response;

import lombok.Builder;

@Builder
public record DetailUserStepResponseDto(

    Long stepId,

    Integer stepOrder,

    String name,

    Double latitude,

    Double longitude,

    String memo
) {

}
