package com.org.olympiccourse.domain.user.controller;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.request.CheckDuplicationRequestDto;
import com.org.olympiccourse.domain.user.request.PasswordCheckRequestDto;
import com.org.olympiccourse.domain.user.request.UserJoinRequestDto;
import com.org.olympiccourse.domain.user.request.UserUpdateRequestDto;
import com.org.olympiccourse.domain.user.response.BasicUserInfoResponse;
import com.org.olympiccourse.domain.user.service.UserService;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<ApiResponse<Object>> join(
        @Valid @RequestBody UserJoinRequestDto userJoinRequestDto) {
        userService.join(userJoinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.successWithoutData(UserResponseCode.USER_CREATED));
    }

    @PostMapping("/check")
    public ResponseEntity<ApiResponse<Object>> checkDuplication(
        @RequestBody CheckDuplicationRequestDto checkDuplicationRequestDto) {
        userService.checkDuplication(checkDuplicationRequestDto);
        return ResponseEntity.ok(
            ApiResponse.successWithoutData(UserResponseCode.DUPLICATION_CHECK_PASSED));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Object>> withdraw(@LoginUser User user) {
        userService.withdraw(user);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.successWithoutData(UserResponseCode.USER_DELETE_SUCCESS));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<BasicUserInfoResponse>> update(@LoginUser User user,
        @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        BasicUserInfoResponse result = userService.update(user, userUpdateRequestDto);
        return ResponseEntity.ok(ApiResponse.success(UserResponseCode.USER_UPDATE_SUCCESS, result));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<BasicUserInfoResponse>> get(@LoginUser User user) {
        BasicUserInfoResponse result = userService.getUserInfo(user);
        return ResponseEntity.ok(ApiResponse.success(UserResponseCode.USER_GET_SUCCESS, result));
    }

    @PostMapping("/me/check-password")
    public ResponseEntity<ApiResponse<Object>> checkPassword(@LoginUser User user,
        @RequestBody PasswordCheckRequestDto passwordCheckRequestDto) {
        userService.checkCurPassword(user, passwordCheckRequestDto);
        return ResponseEntity.ok(
            ApiResponse.success(UserResponseCode.PASSWORD_VERIFY_SUCCESS, null));
    }
}
