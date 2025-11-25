package com.org.olympiccourse.domain.user.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseCode implements ResponseCode {

    // 성공
    USER_CREATED(HttpStatus.CREATED, "USER_CREATED"),
    DUPLICATION_CHECK_PASSED(HttpStatus.OK, "USER_DUPLICATION_CHECK_PASSED"),

    // 실패
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
    WITHDRAWN_USER(HttpStatus.BAD_REQUEST, "WITHDRAWN_USER"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_EMAIL_ALREADY_EXISTS"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_NICKNAME_ALREADY_EXISTS");

    private final HttpStatus httpStatus;
    private final String code;
}
