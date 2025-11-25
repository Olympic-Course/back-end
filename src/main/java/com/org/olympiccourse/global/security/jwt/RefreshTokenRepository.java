package com.org.olympiccourse.global.security.jwt;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "refresh_token:member:";

    public void save(Long userId, String refreshToken) {

        redisTemplate.opsForValue()
            .set(PREFIX + userId, refreshToken, 7, TimeUnit.DAYS);
    }

    public String findByMemberId(String userId) {
        return redisTemplate.opsForValue().get(PREFIX + userId);
    }

    public void delete(String userId) {
        redisTemplate.delete(PREFIX + userId);
    }
}
