package com.org.olympiccourse.global.s3.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ResponseCode implements ResponseCode {

    //성공
    S3_PRESIGNED_URL_CREATED(HttpStatus.CREATED, "S3_PRESIGNED_URL_CREATED");

    //실패


    private final HttpStatus httpStatus;
    private final String code;
}
