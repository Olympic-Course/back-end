package com.org.olympiccourse.domain.admin.code;

import com.org.olympiccourse.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdminResponseCode implements ResponseCode {

    // 성공
    ADMIN_FACILITY_DATA_CREATED(HttpStatus.CREATED, "ADMIN_FACILITY_DATA_CREATED");

    // 실패


    private final HttpStatus httpStatus;
    private final String code;
}
