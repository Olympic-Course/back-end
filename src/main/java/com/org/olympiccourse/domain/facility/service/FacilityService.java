package com.org.olympiccourse.domain.facility.service;

import com.org.olympiccourse.domain.facility.dto.FacilityApiResponse;
import com.org.olympiccourse.domain.facility.dto.FacilityApiResponseWrapper;
import com.org.olympiccourse.domain.facility.entity.Category;
import com.org.olympiccourse.domain.facility.entity.Facility;
import com.org.olympiccourse.domain.facility.entity.RestroomType;
import com.org.olympiccourse.domain.facility.repository.FacilityRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final RestClient restClient;
    private final FacilityRepository facilityRepository;
    @Value("${openapi.facility.service-key}")
    private String SERVICE_KEY;

    @Transactional
    public void importFacility() {
        FacilityApiResponseWrapper response = restClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("api.odcloud.kr")
                .path("/api/15089799/v1/uddi:99c3bcf9-b8a3-4918-80f6-d64d29931df2")
                .queryParam("page", 1)
                .queryParam("perPage", 200)
                .queryParam("returnType", "JSON")
                .queryParam("serviceKey", SERVICE_KEY).build())
            .retrieve()
            .body(FacilityApiResponseWrapper.class);

        List<FacilityApiResponse> facilityApiResponses = response.data();

        List<Facility> facilities = facilityApiResponses.stream()
            .filter(dto -> !dto.category().contains("간이편의점"))
            .filter(dto -> !dto.category().contains("식음료판매점"))
            .map(dto -> {
                    Double lat = toDoubleOrNull(dto.latitude());
                    Double lon = toDoubleOrNull(dto.longitude());
                    if (lat == null || lon == null) {
                        return null;
                    }

                    return Facility.builder()
                        .orderNo(dto.order())
                        .category(parseCategory(dto.category()))
                        .location(dto.location())
                        .detailLocation(dto.detailLocation())
                        .latitude(toDoubleOrNull(dto.latitude()))
                        .longitude(toDoubleOrNull(dto.longitude()))
                        .restroomType(parseRestroomType(dto.category(), dto.memo()))
                        .memo(dto.memo())
                        .build();
                }
            )
            .filter(Objects::nonNull)
            .toList();

        facilityRepository.saveAll(facilities);
    }

    private Double toDoubleOrNull(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }

    }

    private Category parseCategory(String categoryName) {
        return switch (categoryName) {
            case "화장실" -> Category.RESTROOM;
            case "흡연구역" -> Category.SMOKING_BOOTH;
            case "쓰레기통" -> Category.TRASHCAN;
            case "자판기" -> Category.VENDING_MACHINE;
            case "음수대" -> Category.FOUNTAIN;
            default -> throw new IllegalStateException("Unexpected value: " + categoryName);
        };
    }

    private RestroomType parseRestroomType(String category, String memo) {

        if (category.equals("화장실")) {
            if (memo == null || memo.isEmpty()) {
                return RestroomType.ALL;
            }
            if (memo.contains("남자")) {
                return RestroomType.MALE;
            }
            if (memo.contains("여자")) {
                return RestroomType.FEMALE;
            }
        }

        return null;
    }
}
