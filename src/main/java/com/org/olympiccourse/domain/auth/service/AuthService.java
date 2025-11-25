package com.org.olympiccourse.domain.auth.service;

import com.org.olympiccourse.domain.auth.request.LoginRequestDto;
import com.org.olympiccourse.domain.auth.response.LoginResponseDto;
import com.org.olympiccourse.domain.user.response.BasicUserInfoResponse;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import com.org.olympiccourse.global.security.jwt.JwtService;
import com.org.olympiccourse.global.security.jwt.JwtUtil;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public LoginResponseDto login(LoginRequestDto loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        String accessToken = jwtUtil.generateAccessToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        accessTokenSend2Client(headers, accessToken);
        refreshTokenSend2Client(headers, refreshToken, 7);

        return new LoginResponseDto(headers,
            new BasicUserInfoResponse((CustomUserDetails) authentication.getPrincipal()));
    }

    private void accessTokenSend2Client(HttpHeaders headers, String accessToken) {
        headers.set("Authorization", "Bearer " + accessToken);
    }

    private void refreshTokenSend2Client(HttpHeaders headers, String refreshToken, long duration) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofDays(duration))
            .sameSite("None")
            .build();

        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    public HttpHeaders logout(String accessToken, String refreshToken) {
        jwtService.logout(accessToken);

        HttpHeaders headers = new HttpHeaders();
        refreshTokenSend2Client(headers, refreshToken, 0);

        return headers;
    }
}
