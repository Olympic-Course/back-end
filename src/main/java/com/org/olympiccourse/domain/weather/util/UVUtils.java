package com.org.olympiccourse.domain.weather.util;

import com.org.olympiccourse.domain.weather.enums.Grade;

public class UVUtils {

    public static Grade getUVGrade(double value){
        if(value < 3.0){
            return Grade.LOW;
        }else if(value >= 3.0 && value < 6.0){
            return Grade.NORMAL;
        }else if(value >= 6.0 && value < 8.0) {
            return Grade.HIGH;
        }else if(value >= 8.0 && value < 11){
            return Grade.VERY_HIGH;
        }else if(value >= 11){
            return Grade.VERY_DANGEROUS;
        }
        return Grade.UNKNOWN;
    }
}
