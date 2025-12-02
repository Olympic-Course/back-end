package com.org.olympiccourse.domain.map.controller;

import com.org.olympiccourse.domain.facility.dto.FacilityMapResponse;
import com.org.olympiccourse.domain.facility.entity.Category;
import com.org.olympiccourse.domain.map.code.MapResponseCode;
import com.org.olympiccourse.domain.map.service.MapService;
import com.org.olympiccourse.global.response.ApiResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/places")
    public ResponseEntity<ApiResponse<Map<Category, List<FacilityMapResponse>>>> getSpot(
        @RequestParam List<Category> filter) {
        Map<Category, List<FacilityMapResponse>> result = mapService.getSpots(filter);
        return ResponseEntity.ok(ApiResponse.success(MapResponseCode.MAP_GET_SUCCESS, result));
    }
}
