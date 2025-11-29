package com.org.olympiccourse.domain.usercourse.controller;

import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.usercourse.code.UserCourseResponseCode;
import com.org.olympiccourse.domain.usercourse.request.CreateCourseMemoRequestDto;
import com.org.olympiccourse.domain.usercourse.response.UserStepsResponseDto;
import com.org.olympiccourse.domain.usercourse.service.UserCourseService;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class UserCourseController {

    private final UserCourseService userCourseService;

    @PostMapping("/{courseId}/user-courses")
    public ResponseEntity<ApiResponse<UserStepsResponseDto>> createUserCourses(
        @LoginUser User user, @RequestBody
    CreateCourseMemoRequestDto request, @PathVariable Long courseId) {
        UserStepsResponseDto result = userCourseService.createMemo(user, request, courseId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(UserCourseResponseCode.USER_COURSE_STEP_CREATED, result));
    }

    @PutMapping("/{courseId}/user-courses/{userCourseId}")
    public ResponseEntity<ApiResponse<UserStepsResponseDto>> updateUserCourses(
        @LoginUser User user, @RequestBody CreateCourseMemoRequestDto request,
        @PathVariable Long courseId, @PathVariable Long userCourseId) {

        UserStepsResponseDto result = userCourseService.updateMemo(user, request, courseId,
            userCourseId);

        return ResponseEntity.ok(
            ApiResponse.success(UserCourseResponseCode.USER_COURSE_STEP_UPDATE_SUCCESS, result));
    }
}
