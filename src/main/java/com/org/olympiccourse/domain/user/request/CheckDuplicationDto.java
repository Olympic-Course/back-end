package com.org.olympiccourse.domain.user.request;

import lombok.Getter;

@Getter
public class CheckDuplicationDto {

    private Type type;
    private String content;
}
