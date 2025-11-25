package com.org.olympiccourse.domain.auth.controller;

import com.org.olympiccourse.domain.auth.code.AuthResponseCode;
import com.org.olympiccourse.domain.auth.request.LoginRequestDto;
import com.org.olympiccourse.domain.auth.response.LoginResponseDto;
import com.org.olympiccourse.domain.auth.service.AuthService;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            .body(ApiResponse.success(AuthResponseCode.LOGIN_SUCCESS, result.getMemberInfo()));
    }
}
