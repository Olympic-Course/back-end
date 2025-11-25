package com.org.olympiccourse.domain.user.request;

import lombok.Getter;

@Getter
public class CheckDuplicationRequestDto {

    private Type type;
    private String content;
}
