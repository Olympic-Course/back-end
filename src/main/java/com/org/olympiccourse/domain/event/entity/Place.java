package com.org.olympiccourse.domain.event.entity;

public enum Place {

    KSPO_DOME("KSPO DOME (올림픽체조경기장)"),
    TICKETLINK_LIVE_ARENA("티켓링크 라이브 아레나 (핸드볼경기장)"),
    OLYMPIC_HALL("올림픽홀"),
    MUSE_LIVE("뮤즈라이브"),
    WOORI_ART_HALL("우리금융아트홀 (올림픽역도경기장)"),
    K_ART_HALL("K-아트홀"),
    OUTDOOR_ETC("옥외/기타");

    private final String name;

    Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
