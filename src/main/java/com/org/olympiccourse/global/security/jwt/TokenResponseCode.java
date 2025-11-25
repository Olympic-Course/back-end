package com.org.olympiccourse.global.security.jwt;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TokenResponseCode implements ResponseCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TKN_INVALID_TOKEN"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TKN_EXPIRED_ACCESS_TOKEN"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TKN_EXPIRED_REFRESH_TOKEN"),
    ACCESS_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED,"TKN_ACCESS_TOKEN_NOT_EXIST"),
    REFRESH_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED,"TKN_REFRESH_TOKEN_NOT_EXIST");

    private final HttpStatus httpStatus;
    private final String code;
}
