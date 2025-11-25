package com.org.olympiccourse.domain.user.response;

import com.org.olympiccourse.domain.user.entity.Language;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.security.basic.CustomUserDetails;
import lombok.Getter;

@Getter
public class BasicUserInfoResponse {

    private Long userId;

    private String email;

    private String nickname;

    private Language language;

    public BasicUserInfoResponse(CustomUserDetails customUserDetails) {

        User user = customUserDetails.getUser();
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.language = user.getLanguage();
    }
}
