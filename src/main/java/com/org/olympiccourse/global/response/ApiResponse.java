package com.org.olympiccourse.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final String code;
    private final T data;

    private ApiResponse(boolean success, String code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String code, T data) {
        return new ApiResponse<>(true, code, data);
    }

    public static <T> ApiResponse<T> successWithoutData(String code) {
        return new ApiResponse<>(true, code, null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), null);
    }
}
