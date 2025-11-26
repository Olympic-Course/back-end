package com.org.olympiccourse.domain.coursephoto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhotoCreateRequestDto(

    @NotBlank
    String path,

    @NotNull
    Boolean isRep
) {

}
