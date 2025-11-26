package com.org.olympiccourse.global.security.jwt;

import com.org.olympiccourse.domain.auth.code.AuthResponseCode;
import com.org.olympiccourse.domain.auth.response.NewTokenResponse;
import com.org.olympiccourse.global.response.CustomException;
import com.org.olympiccourse.global.response.GlobalErrorCode;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (!refreshTokenRepository.findByUserId(claimsAccessToken.getSubject()).isEmpty()) {
            refreshTokenRepository.delete(claimsAccessToken.getSubject());
        }
    }

    public NewTokenResponse reissue(String accessToken, String refreshToken) {

        String extractAccessToken = jwtUtil.removePrefixFromAccessToken(accessToken);

        Claims claimsAccessToken = jwtUtil.extractClaimsOrThrow("accessToken", extractAccessToken);
        Claims claimsRefreshToken = jwtUtil.extractClaimsOrThrow("refreshToken", refreshToken);

        String userIdFromAccessToken = claimsAccessToken.getSubject();
        String userIdFromRefreshToken = claimsRefreshToken.getSubject();

        if (!userIdFromRefreshToken.equals(userIdFromAccessToken)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        if (jwtUtil.checkBlacklist(extractAccessToken)) {
            logout(accessToken);
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        jwtUtil.validateAccessTokenExpiration(claimsAccessToken, extractAccessToken);

        String existingRefreshToken = refreshTokenRepository.findByUserId(
            userIdFromRefreshToken);
        if (existingRefreshToken == null) {
            throw new CustomException(AuthResponseCode.UNAUTHORIZED);
        }

        Authentication authentication = jwtUtil.getAuthentication(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(authentication);
        String newRefreshToken = jwtUtil.generateRefreshToken(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
            customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return new NewTokenResponse(newAccessToken, newRefreshToken);
    }
}
