package com.org.olympiccourse.global.response;

public class CustomException extends RuntimeException {

    private final ResponseCode errorCode;

    public CustomException(ResponseCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public ResponseCode getErrorCode() {return errorCode;}
}
