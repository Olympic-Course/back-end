package com.org.olympiccourse.domain.user.request;

import com.org.olympiccourse.domain.user.entity.Language;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserJoinRequestDto {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotNull
    private Language language;
}
