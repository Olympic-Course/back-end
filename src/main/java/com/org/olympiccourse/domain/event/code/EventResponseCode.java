package com.org.olympiccourse.domain.event.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventResponseCode implements ResponseCode {

    //성공
    EVENT_GET_SUCCESS(HttpStatus.OK,  "EVENT_GET_SUCCESS"),;
    //실패

    private final HttpStatus httpStatus;
    private final String code;
}