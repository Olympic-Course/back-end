package com.org.olympiccourse.domain.user.controller;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.request.CheckDuplicationDto;
import com.org.olympiccourse.domain.user.request.UserJoinDto;
import com.org.olympiccourse.domain.user.service.UserService;
import com.org.olympiccourse.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> join(@Valid @RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.successWithoutData(UserResponseCode.USER_CREATED));
    }

    @PostMapping("/check")
    public ResponseEntity<ApiResponse<Object>> checkDuplication(
        @RequestBody CheckDuplicationDto checkDuplicationDto) {
        userService.checkDuplication(checkDuplicationDto);
        return ResponseEntity.ok(
            ApiResponse.successWithoutData(UserResponseCode.USER_DUPLICATION_CHECK_PASSED));
    }
}
