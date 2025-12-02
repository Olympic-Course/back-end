package com.org.olympiccourse.domain.facility.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.org.olympiccourse.domain.facility.entity.Category;
import com.org.olympiccourse.domain.facility.entity.RestroomType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FacilityMapResponse {

    private Long placeId;
    private String name;
    private double latitude;
    private double longitude;
    private Category category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RestroomType restroomType;
}
