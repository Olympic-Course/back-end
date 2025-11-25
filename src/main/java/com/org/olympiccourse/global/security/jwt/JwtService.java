package com.org.olympiccourse.global.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void logout(String accessToken) {

        String extractAccessToken = jwtUtil.removePrefixFromAccessToken(accessToken);
        Claims claimsAccessToken = jwtUtil.extractClaimsOrThrow("accessToken", extractAccessToken);

        jwtUtil.addBlackListExistingAccessToken(extractAccessToken,
            claimsAccessToken.getExpiration());

        if (!refreshTokenRepository.findByMemberId(claimsAccessToken.getSubject()).isEmpty()) {
            refreshTokenRepository.delete(claimsAccessToken.getSubject());
        }
    }
}
