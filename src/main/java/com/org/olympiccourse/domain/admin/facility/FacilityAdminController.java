package com.org.olympiccourse.domain.admin.facility;

import com.org.olympiccourse.domain.admin.code.AdminResponseCode;
import com.org.olympiccourse.domain.facility.service.FacilityService;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/facilities")
public class FacilityAdminController {

    private final FacilityService facilityService;

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<Void>> importFacilities() {
        facilityService.importFacility();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.successWithoutData(
            AdminResponseCode.ADMIN_FACILITY_DATA_CREATED));
    }

}
