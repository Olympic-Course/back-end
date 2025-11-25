package com.org.olympiccourse.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Language {
    KO,
    EN;

    @JsonCreator
    public static Language from(String value) {
        return Language.valueOf(value.toUpperCase());
    }
}
