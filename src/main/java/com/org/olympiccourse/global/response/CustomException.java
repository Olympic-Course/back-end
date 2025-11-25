package com.org.olympiccourse.global.response;

public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {return errorCode;}
}
