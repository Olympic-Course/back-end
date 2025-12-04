package com.org.olympiccourse.domain.like.controller;

import com.org.olympiccourse.domain.like.code.LikeResponseCode;
import com.org.olympiccourse.domain.like.dto.LikeProcessResponse;
import com.org.olympiccourse.domain.like.service.LikeService;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{courseId}/like")
    public ResponseEntity<ApiResponse<LikeProcessResponse>> like(@PathVariable Long courseId, @LoginUser User user) {
        LikeProcessResponse result = likeService.likeProcess(courseId, user);
        return ResponseEntity.ok(ApiResponse.success(LikeResponseCode.LIKE_PROCESS_SUCCESS, result));
    }
}
