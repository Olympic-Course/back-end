package com.org.olympiccourse.domain.auth.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @Email
    private String email;
    private String password;
}
