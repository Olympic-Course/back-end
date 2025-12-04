package com.org.olympiccourse.domain.weather.enums;

public enum Sky {
    UNKNOWN,
    CLEAR,
    CLOUDY,
    FOG;

    public static Sky getSky(String value) {
        switch (value) {
            case "1" -> {
                return Sky.CLEAR;
            }
            case "3" -> {
                return Sky.CLOUDY;
            }
            case "4" -> {
                return Sky.FOG;
            }
            default -> {
                return Sky.UNKNOWN;
            }
        }
    }
}
