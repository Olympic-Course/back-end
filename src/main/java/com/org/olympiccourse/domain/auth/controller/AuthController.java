package com.org.olympiccourse.domain.auth.controller;

import com.org.olympiccourse.domain.auth.code.AuthResponseCode;
import com.org.olympiccourse.domain.auth.request.LoginRequestDto;
import com.org.olympiccourse.domain.auth.response.LoginResponseDto;
import com.org.olympiccourse.domain.auth.service.AuthService;
import com.org.olympiccourse.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto result = authService.login(loginRequestDto);
        return ResponseEntity.ok()
            .headers(result.getHttpHeaders())
            .body(ApiResponse.success(AuthResponseCode.LOGIN_SUCCESS, result.getUserInfo()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
        @Parameter(hidden = true) @RequestHeader("Authorization") String accessToken,
        @Parameter(hidden = true) @CookieValue(name = "refresh-token") String refreshToken
    ) {
        HttpHeaders result = authService.logout(accessToken, refreshToken);
        return ResponseEntity.ok().headers(result)
            .body(ApiResponse.successWithoutData(AuthResponseCode.LOGOUT_SUCCESS));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Object>> reissue(
        @RequestHeader("Authorization") String accessToken,
        @Parameter(hidden = true) @CookieValue(name = "refresh-token") String refreshToken) {

        HttpHeaders result = authService.reissue(accessToken, refreshToken);
        return ResponseEntity.ok().headers(result)
            .body(ApiResponse.successWithoutData(AuthResponseCode.REISSUE_SUCCESS));
    }
}
