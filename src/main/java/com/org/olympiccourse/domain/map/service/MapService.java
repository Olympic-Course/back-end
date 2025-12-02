package com.org.olympiccourse.domain.map.service;

import com.org.olympiccourse.domain.facility.dto.FacilityMapResponse;
import com.org.olympiccourse.domain.facility.entity.Category;
import com.org.olympiccourse.domain.facility.entity.Facility;
import com.org.olympiccourse.domain.facility.repository.FacilityRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapService {

    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public Map<Category, List<FacilityMapResponse>> getSpots(List<Category> filter) {

        List<Facility> findFacilities = facilityRepository.findByCategoryIn(filter);
        Map<Category, List<FacilityMapResponse>> facilityMapResponses = findFacilities.stream()
            .map(entity ->
                FacilityMapResponse.builder()
                    .placeId(entity.getId())
                    .name(getName(entity.getLocation(), entity.getDetailLocation(),
                        entity.getCategory().getKorean()))
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .category(entity.getCategory())
                    .restroomType(entity.getRestroomType())
                    .build()).collect(Collectors.groupingBy(FacilityMapResponse::getCategory));

        return facilityMapResponses;
    }

    private String getName(String location, String detailLocation, String category) {
        String detail = (detailLocation != null && !detailLocation.isBlank())
            ? " " + detailLocation
            : "";
        return location + detail + " " + category;
    }
}
