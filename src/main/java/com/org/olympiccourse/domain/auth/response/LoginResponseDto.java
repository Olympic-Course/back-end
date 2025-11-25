package com.org.olympiccourse.domain.auth.response;

import com.org.olympiccourse.domain.user.response.BasicUserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponseDto {

    private HttpHeaders httpHeaders;
    private BasicUserInfoResponse memberInfo;
}
