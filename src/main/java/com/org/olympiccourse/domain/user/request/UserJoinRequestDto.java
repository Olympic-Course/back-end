package com.org.olympiccourse.domain.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserJoinRequestDto {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
