package com.org.olympiccourse.global.response;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String getCode();
}

