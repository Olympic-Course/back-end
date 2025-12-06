package com.org.olympiccourse.domain.course.request;

import com.org.olympiccourse.domain.course.entity.Cost;
import com.org.olympiccourse.domain.course.entity.Duration;
import com.org.olympiccourse.domain.coursestep.request.StepRequestDto;
import com.org.olympiccourse.domain.tag.entity.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateCourseRequestDto(

    @NotBlank
    String title,

    @NotNull
    Boolean secret,

    @NotNull
    List<@NotNull Tag> tag,

    String comment,

    @Size(min = 1)
    @NotNull
    List<@NotNull @Valid StepRequestDto> steps,

    Duration duration,

    Cost cost
) {

}
