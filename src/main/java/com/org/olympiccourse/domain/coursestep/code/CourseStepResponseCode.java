package com.org.olympiccourse.domain.coursestep.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CourseStepResponseCode implements ResponseCode {

    //성공

    //실패
    COURSE_STEP_NOT_FOUND(HttpStatus.NOT_FOUND,  "COURSE_STEP_NOT_FOUND"),
    COURSE_STEP_NOT_BELONG_TO_COURSE(HttpStatus.BAD_REQUEST, "COURSE_STEP_NOT_BELONG_TO_COURSE");

    private final HttpStatus httpStatus;
    private final String code;
}