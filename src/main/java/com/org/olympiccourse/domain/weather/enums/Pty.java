package com.org.olympiccourse.domain.weather.enums;

public enum Pty {
    UNKNOWN,
    NONE,
    RAIN,
    RAIN_SNOW,
    SNOW;

    public static Pty getPty(String value) {
        switch (value) {
            case "0" -> {
                return Pty.NONE;
            }
            case "1", "5" -> {
                return Pty.RAIN;
            }
            case "2", "6" -> {
                return Pty.RAIN_SNOW;
            }
            case "3", "7" -> {
                return Pty.SNOW;
            }
            default -> {
                return Pty.UNKNOWN;
            }
        }
    }
}
