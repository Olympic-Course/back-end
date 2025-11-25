package com.org.olympiccourse.global.response;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    HttpStatus getHttpStatus();
    String getCode();
}

