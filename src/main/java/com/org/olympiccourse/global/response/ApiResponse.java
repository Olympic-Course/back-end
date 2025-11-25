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

    public static <T> ApiResponse<T> success(ResponseCode code, T data) {
        return new ApiResponse<>(true, code.getCode(), data);
    }

    public static <T> ApiResponse<T> successWithoutData(ResponseCode code) {
        return new ApiResponse<>(true, code.getCode(), null);
    }

    public static <T> ApiResponse<T> fail(ResponseCode code) {
        return new ApiResponse<>(false, code.getCode(), null);
    }
}
