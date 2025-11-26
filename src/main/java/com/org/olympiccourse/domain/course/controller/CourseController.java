package com.org.olympiccourse.domain.course.controller;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.request.CreateCourseRequestDto;
import com.org.olympiccourse.domain.course.response.CreateCourseResponseDto;
import com.org.olympiccourse.domain.course.service.CourseService;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<CreateCourseResponseDto>> createCourse(@LoginUser User user,
        @RequestBody CreateCourseRequestDto request) {

        CreateCourseResponseDto result = courseService.create(user, request);
        return ResponseEntity.ok(ApiResponse.success(CourseResponseCode.COURSE_CREATED, result));
    }
}
