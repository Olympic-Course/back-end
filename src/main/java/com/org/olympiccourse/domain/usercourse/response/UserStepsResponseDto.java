package com.org.olympiccourse.domain.usercourse.response;

import java.util.List;

public record UserStepsResponseDto(
    Long userCourseId,
    List<DetailUserStepResponseDto> userSteps
) {

}
