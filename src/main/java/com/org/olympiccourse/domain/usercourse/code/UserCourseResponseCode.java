package com.org.olympiccourse.domain.usercourse.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserCourseResponseCode implements ResponseCode {

    //성공
    USER_COURSE_STEP_CREATED(HttpStatus.CREATED, "USER_COURSE_STEP_CREATED"),
    USER_COURSE_STEP_UPDATE_SUCCESS(HttpStatus.OK, "USER_COURSE_STEP_UPDATE_SUCCESS"),

    //실패
    USER_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_COURSE_NOT_FOUND"),;

    private final HttpStatus httpStatus;
    private final String code;
}
