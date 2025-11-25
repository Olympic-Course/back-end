package com.org.olympiccourse.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"BAD_REQUEST"),;

    private final HttpStatus httpStatus;
    private final String code;
}
