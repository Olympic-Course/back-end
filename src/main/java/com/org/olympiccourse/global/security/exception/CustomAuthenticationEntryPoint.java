package com.org.olympiccourse.global.security.exception;

import com.org.olympiccourse.global.security.jwt.TokenResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        String exceptionMessage = (String) request.getAttribute("exception");
        String errorCode = "";

        if("EXPIRED_ACCESS_TOKEN".equals(exceptionMessage)){
            log.error("EXPIRED_ACCESS_TOKEN");
            errorCode = TokenResponseCode.EXPIRED_ACCESS_TOKEN.getCode();
        }else if("EXPIRED_REFRESH_TOKEN".equals(exceptionMessage)){
            log.error("EXPIRED_REFRESH_TOKEN");
            errorCode = TokenResponseCode.EXPIRED_REFRESH_TOKEN.getCode();
        }else if("INVALID_TOKEN".equals(exceptionMessage)){
            log.error("INVALID_TOKEN");
            errorCode = TokenResponseCode.INVALID_TOKEN.getCode();
        }else if("ACCESS_TOKEN_NOT_EXIST".equals(exceptionMessage)){
            log.error("ACCESS_TOKEN_NOT_EXIST");
            errorCode = TokenResponseCode.ACCESS_TOKEN_NOT_EXIST.getCode();
        }else if("REFRESH_TOKEN_EXPIRED".equals(exceptionMessage)){
            log.error("REFRESH_TOKEN_EXPIRED");
            errorCode = TokenResponseCode.REFRESH_TOKEN_NOT_EXIST.getCode();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"success\" : false, \"code\" : " + errorCode + ", \"data\" : null}");
    }
}
