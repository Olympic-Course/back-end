package com.org.olympiccourse.domain.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class AirApiResponseWrapper {

    @JsonProperty("ListAirQualityByDistrictService")
    private AirQualityBody listAirQualityByDistrictService;

    @Getter
    @NoArgsConstructor
    public static class AirQualityBody {

        @JsonProperty("list_total_count")
        private Long listTotalCount;

        @JsonProperty("RESULT")
        private Result result;
        private List<Row> row;
    }
    @Getter
    @NoArgsConstructor
    public static class Result {
        @JsonProperty("CODE")
        private String code;

        @JsonProperty("MESSAGE")
        private String message;
    }

    @Getter
    @NoArgsConstructor
    public static class Row {

        @JsonProperty("MSRMT_YMD")
        private String msrmtYmd;
        @JsonProperty("MSRSTN_PBADMS_CD")
        private String msrstnPbadmsCd;
        @JsonProperty("MSRSTN_NM")
        private String msrstnNm;
        @JsonProperty("CAI")
        private String cai;
        @JsonProperty("CAI_GRD")
        private String caiGrd;
        @JsonProperty("CRST_SBSTN")
        private String crstSbstn;
        @JsonProperty("NTDX")
        private String ntdx;
        @JsonProperty("OZON")
        private String ozon;
        @JsonProperty("CBMX")
        private String cbmx;
        @JsonProperty("SPDX")
        private String spdx;
        @JsonProperty("PM")
        private String pm;
        @JsonProperty("FPM")
        private String fpm;
    }
}
