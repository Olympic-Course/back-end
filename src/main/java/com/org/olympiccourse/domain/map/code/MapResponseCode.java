package com.org.olympiccourse.domain.map.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MapResponseCode implements ResponseCode {

    //성공
    MAP_GET_SUCCESS(HttpStatus.OK, "MAP_GET_SUCCESS");

    //실패

    private final HttpStatus httpStatus;
    private final String code;
}
