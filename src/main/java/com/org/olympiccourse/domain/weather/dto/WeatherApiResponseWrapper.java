package com.org.olympiccourse.domain.weather.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherApiResponseWrapper {
    private WeatherApiResponse response;

    @Getter
    @NoArgsConstructor
    public static class WeatherApiResponse {
        private Header header;
        private WeatherBody body;
    }

    @Getter
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    public static class WeatherBody {
        private String dataType;
        private ItemList items;
    }

    @Getter
    @NoArgsConstructor
    public static class ItemList {
        private List<Item> item;
    }

    @Getter
    @NoArgsConstructor
    public static class Item {
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private String nx;
        private String ny;
    }
}
