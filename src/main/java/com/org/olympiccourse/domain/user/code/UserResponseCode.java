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

    // 실패
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_EMAIL_ALREADY_EXISTS"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_NICKNAME_ALREADY_EXISTS");

    private final HttpStatus httpStatus;
    private final String code;
}
