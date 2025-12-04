package com.org.olympiccourse.global.s3.controller;

import com.org.olympiccourse.global.response.ApiResponse;
import com.org.olympiccourse.global.s3.code.S3ResponseCode;
import com.org.olympiccourse.global.s3.dto.PresignRequestDto;
import com.org.olympiccourse.global.s3.dto.PresignedUrlResponse;
import com.org.olympiccourse.global.s3.service.S3PresignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class S3Controller {

    private final S3PresignService s3PresignService;


    @PostMapping("presigned")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> imagePresignedUrl(
        @RequestBody PresignRequestDto request) {
        PresignedUrlResponse result = s3PresignService.createUploadUrl(request);
        return ResponseEntity.ok(
            ApiResponse.success(S3ResponseCode.S3_PRESIGNED_URL_CREATED, result));
    }
}
