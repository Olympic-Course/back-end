package com.org.olympiccourse.domain.home.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HomeResponseCode implements ResponseCode {

    //성공
    HOME_GET_SUCCESS(HttpStatus.OK, "HOME_GET_SUCCESS");

    //실패

    private final HttpStatus httpStatus;
    private final String code;
}
