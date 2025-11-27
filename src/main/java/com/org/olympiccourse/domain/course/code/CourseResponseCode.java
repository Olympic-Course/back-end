package com.org.olympiccourse.domain.course.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CourseResponseCode implements ResponseCode {

    //성공
    COURSE_CREATED(HttpStatus.CREATED, "COURSE_CREATED"),
    COURSE_GET_SUCCESS(HttpStatus.OK, "COURSE_GET_SUCCESS"),
    COURSE_DELETE_SUCCESS(HttpStatus.OK, "COURSE_DELETE_SUCCESS"),
    COURSE_UPDATE_SUCCESS(HttpStatus.OK, "COURSE_UPDATE_SUCCESS"),

    //실패
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND,  "COURSE_NOT_FOUND"),
    COURSE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "COURSE_ACCESS_DENIED"),;

    private final HttpStatus httpStatus;
    private final String code;
}
