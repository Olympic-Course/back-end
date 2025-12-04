package com.org.olympiccourse.domain.event.dto;

import lombok.Builder;

@Builder
public record EventInfoResponse(
    String name,
    String place
) {

}
