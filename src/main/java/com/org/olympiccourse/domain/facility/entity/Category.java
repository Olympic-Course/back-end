package com.org.olympiccourse.domain.facility.entity;

public enum Category {
    TRASHCAN ("쓰레기통"),
    FOUNTAIN("음수대"),
    SMOKING_BOOTH("흡연구역"),
    VENDING_MACHINE("자판기"),
    RESTROOM("화장실");

    private final String korean;

    Category(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}
