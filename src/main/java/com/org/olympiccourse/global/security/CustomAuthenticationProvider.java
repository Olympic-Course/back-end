package com.org.olympiccourse.global.security;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.global.response.CustomException;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import com.org.olympiccourse.global.security.basic.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails customUserDetails = null;

        try {
            customUserDetails = customUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new CustomException(UserResponseCode.USER_NOT_FOUND);
        } catch (DisabledException e) {
            throw new CustomException(UserResponseCode.WITHDRAWN_USER);
        }

        if(password != null && !passwordEncoder.matches(password, customUserDetails.getPassword())) {
            throw new CustomException(UserResponseCode.USER_NOT_FOUND);
        }

        return new UsernamePasswordAuthenticationToken(customUserDetails, null,
            customUserDetails.getAuthorities());
    }
}
