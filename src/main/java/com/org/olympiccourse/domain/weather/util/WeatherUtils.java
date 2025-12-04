package com.org.olympiccourse.domain.weather.util;

public class WeatherUtils {

    // 습구온도
    public static double calculateWetBulb(double temperature, double humidity) {
        double rh = humidity;
        double ta = temperature;

        return ta * Math.atan(0.151977 * Math.sqrt(rh + 8.313659))
            + Math.atan(ta + rh)
            - Math.atan(rh - 1.67633)
            + 0.00391838 * Math.pow(rh, 1.5) * Math.atan(0.023101 * rh)
            - 4.686035;
    }

    // 여름 체감온도
    public static double calculateSummerFeelsLike(double temperature, double humidity) {
        double tw = calculateWetBulb(temperature, humidity);
        double ta = temperature;

        return -0.2442
            + 0.55399 * tw
            + 0.45535 * ta
            - 0.0022 * tw * tw
            + 0.00278 * tw * ta
            + 3.0;
    }

    // 겨울 체감온도
    public static double calculateWinterFeelsLike(double temperature, double windSpeed) {
        double ta = temperature;
        double v = windSpeed;

        if (ta <= 10 && v > 4.8) {
            return 13.12
                + 0.6215 * ta
                - 11.37 * Math.pow(v, 0.16)
                + 0.3965 * ta * Math.pow(v, 0.16);
        }
        return temperature;
    }
}
