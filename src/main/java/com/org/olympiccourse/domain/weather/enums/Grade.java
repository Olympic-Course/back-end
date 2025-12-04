package com.org.olympiccourse.domain.weather.enums;

public enum Grade {

    UNKNOWN("알수없음"),
    GOOD("좋음"),
    NORMAL("보통"),
    BAD("나쁨"),
    VERY_BAD("매우 나쁨"),
    LOW("낮음"),           // 자외선 전용
    HIGH("높음"),         // 자외선 전용
    VERY_HIGH("매우 높음"), // 자외선 전용
    VERY_DANGEROUS("매우 위험"); // 자외선 전용

    private final String name;
    Grade(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
