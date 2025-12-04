package com.org.olympiccourse.domain.user.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
    Long userId,

    String email,

    String nickname,

    Long postCount,

    Long likedPostCount
) {

}
