package com.org.olympiccourse.global.security.filter;

import com.org.olympiccourse.global.response.CustomException;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import com.org.olympiccourse.global.security.jwt.JwtUtil;
import com.org.olympiccourse.global.security.jwt.TokenResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String EXCEPTION_ATTRIBUTE = "exception";
    private final JwtUtil jwtUtil;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final Set<String> excludeAllPaths = Set.of(
        "/api/auth/login", "/api/users", "/api/users/check"
    );

    private final Set<String> excludeGetPaths = Set.of("/api/courses/**");

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        if (isExcludedPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = null;

        try {
            accessToken = jwtUtil.removePrefixFromAccessToken(request.getHeader("Authorization"));
            jwtUtil.parseToken(accessToken);
        } catch (CustomException e) {
            if (e.getErrorCode() == TokenResponseCode.ACCESS_TOKEN_NOT_EXIST) {
                request.setAttribute(EXCEPTION_ATTRIBUTE, "ACCESS_TOKEN_NOT_EXIST");
                throw new CustomException(TokenResponseCode.ACCESS_TOKEN_NOT_EXIST);
            }
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            request.setAttribute(EXCEPTION_ATTRIBUTE, "INVALID_TOKEN");
            throw new CustomException(TokenResponseCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {

            request.setAttribute(EXCEPTION_ATTRIBUTE, "EXPIRED_ACCESS_TOKEN");
            throw new CustomException(TokenResponseCode.EXPIRED_ACCESS_TOKEN);
        }

        if (jwtUtil.checkBlacklist(accessToken)) {
            request.setAttribute(EXCEPTION_ATTRIBUTE, "ACCESS_TOKEN_NOT_EXIST");
            throw new CustomException(TokenResponseCode.ACCESS_TOKEN_NOT_EXIST);
        }

        Authentication authentication = jwtUtil.getAuthentication(accessToken);
        log.info("로그인한 사용자: {}",
            ((CustomUserDetails) authentication.getPrincipal()).getUser().getNickname());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean matchAll = excludeAllPaths.stream()
            .anyMatch(pattern -> antPathMatcher.match(pattern, path));

        if (matchAll) {
            return true;
        }

        if ("GET".equals(method)) {
            boolean matchGet = excludeGetPaths.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, path));

            if (matchGet) {
                String authHeader = request.getHeader("Authorization");
                if (authHeader == null || authHeader.isBlank()) {
                    return true;
                }
                return false;
            }
        }

        return false;
    }
}
