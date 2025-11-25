package com.org.olympiccourse.domain.auth.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthResponseCode implements ResponseCode {

    // 성공
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH_LOGIN_SUCCESS"),

    // 실패
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_UNAUTHORIZED");

    private final HttpStatus httpStatus;
    private final String code;
}
