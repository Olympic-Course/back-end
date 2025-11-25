package com.org.olympiccourse.domain.user.request;

import com.org.olympiccourse.domain.user.entity.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private String nickname;

    private Language language;
}
