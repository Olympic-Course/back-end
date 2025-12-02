package com.org.olympiccourse.domain.facility.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FacilityApiResponse(

    @JsonProperty("순번")
    int order,

    @JsonProperty("구분")
    String category,

    @JsonProperty("위치")
    String location,

    @JsonProperty("상세위치")
    String detailLocation,

    @JsonProperty("위도")
    String latitude,

    @JsonProperty("경도")
    String longitude,

    @JsonProperty("비고")
    String memo
) {

}
