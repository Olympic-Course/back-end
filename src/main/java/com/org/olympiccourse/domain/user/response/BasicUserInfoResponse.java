package com.org.olympiccourse.domain.user.response;

import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BasicUserInfoResponse {

    private Long userId;

    private String email;

    private String nickname;


    public BasicUserInfoResponse(CustomUserDetails customUserDetails) {

        User user = customUserDetails.getUser();
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
