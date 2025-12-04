package com.org.olympiccourse.domain.weather.util;

import com.org.olympiccourse.domain.weather.enums.Grade;

public class AirUtils {

    public static Grade getPMGrade(String value) {
        int v = Integer.valueOf(value);
        if (v <= 30) {
            return Grade.GOOD;
        } else if (v >= 31 && v <= 80) {
            return Grade.NORMAL;
        } else if (v >= 81 && v <= 150) {
            return Grade.BAD;
        } else if (v >= 151) {
            return Grade.VERY_BAD;
        }
        return Grade.UNKNOWN;
    }

    public static Grade getFPMGrade(String value) {
        int v = Integer.valueOf(value);
        if (v <= 15) {
            return Grade.GOOD;
        } else if (v >= 16 && v <= 35) {
            return Grade.NORMAL;
        } else if (v >= 36 && v <= 75) {
            return Grade.BAD;
        } else if (v >= 76) {
            return Grade.VERY_BAD;
        }
        return Grade.UNKNOWN;
    }
}
