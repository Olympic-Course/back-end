package com.org.olympiccourse.domain.user.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Type {
    NICKNAME,
    EMAIL;

    @JsonCreator
    public static Type from(String value) {
        return Type.valueOf(value.toUpperCase());
    }
}
