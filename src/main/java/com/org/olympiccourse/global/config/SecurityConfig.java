package com.org.olympiccourse.global.config;

import com.org.olympiccourse.global.security.CustomAuthenticationProvider;
import com.org.olympiccourse.global.security.basic.CustomUserDetailsService;
import com.org.olympiccourse.global.security.exception.CustomAuthenticationEntryPoint;
import com.org.olympiccourse.global.security.filter.JwtAuthorizationFilter;
import com.org.olympiccourse.global.security.jwt.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder());
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtUtil jwtUtil) {
        return new JwtAuthorizationFilter(jwtUtil);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(customAuthenticationProvider()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                // 로그인, 회원가입, 중복체크
                .requestMatchers("/api/auth/login", "/api/users", "/api/users/check"
                ).permitAll()
                // 코스
                .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
                // 지도
                .requestMatchers("/api/map/places").permitAll()
                // 홈
                .requestMatchers("/api/home").permitAll()
                .anyRequest().authenticated()
            );

        // 기본 로그인 관련 설정
        http
            .formLogin(auth -> auth.disable())
            .httpBasic(auth -> auth.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        http
            .addFilterAfter(jwtAuthorizationFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(
            exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        return http.build();
    }
}
