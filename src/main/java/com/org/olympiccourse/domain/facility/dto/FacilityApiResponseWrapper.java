package com.org.olympiccourse.domain.facility.dto;

import java.util.List;

public record FacilityApiResponseWrapper(
    int page,
    int perPage,
    int totalCount,
    int currentCount,
    int matchCount,
    List<FacilityApiResponse> data
) {

}
