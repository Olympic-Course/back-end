package com.org.olympiccourse.domain.like.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeResponseCode implements ResponseCode {

    //성공
    LIKE_PROCESS_SUCCESS(HttpStatus.OK, "LIKE_PROCESS_SUCCESS");

    //실패

    private final HttpStatus httpStatus;
    private final String code;
}
