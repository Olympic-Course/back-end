package com.org.olympiccourse.domain.usercourse.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserCourseResponseCode implements ResponseCode {

    //성공
    USER_COURSE_STEP_CREATED(HttpStatus.CREATED, "USER_COURSE_STEP_CREATED");

    //실패


    private final HttpStatus httpStatus;
    private final String code;
}
