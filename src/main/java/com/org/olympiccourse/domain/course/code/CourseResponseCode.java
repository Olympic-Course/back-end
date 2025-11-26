package com.org.olympiccourse.domain.course.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CourseResponseCode implements ResponseCode {

    //성공
    COURSE_CREATED(HttpStatus.CREATED, "COURSE_CREATED");

    private final HttpStatus httpStatus;
    private final String code;
}
