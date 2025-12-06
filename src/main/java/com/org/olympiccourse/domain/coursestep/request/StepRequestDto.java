package com.org.olympiccourse.domain.coursestep.request;

import com.org.olympiccourse.domain.coursephoto.request.PhotoCreateRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record StepRequestDto(

    @NotNull
    Integer stepOrder,

    @NotBlank
    String name,

    @NotNull
    Double latitude,

    @NotNull
    Double longitude,

    String description,

    @Size(min = 1)
    @NotNull
    List<@NotNull @Valid PhotoCreateRequestDto> photos
) {

}
