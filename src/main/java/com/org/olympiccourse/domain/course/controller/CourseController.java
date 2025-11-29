package com.org.olympiccourse.domain.course.controller;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.request.CourseSearchCond;
import com.org.olympiccourse.domain.course.request.CreateCourseRequestDto;
import com.org.olympiccourse.domain.course.request.MyCourseVisibility;
import com.org.olympiccourse.domain.course.response.CourseListResponseDto;
import com.org.olympiccourse.domain.course.response.CourseSimpleListWithTagResponseDto;
import com.org.olympiccourse.domain.course.response.CreateCourseResponseDto;
import com.org.olympiccourse.domain.course.response.DetailReadCourseResponseDto;
import com.org.olympiccourse.domain.course.service.CourseService;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.annotation.LoginUser;
import com.org.olympiccourse.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<CourseListResponseDto>> getCourseList(@LoginUser User user,
        CourseSearchCond condition) {
        CourseListResponseDto result = courseService.getCourseList(user, condition);
        return ResponseEntity.ok(
            ApiResponse.success(CourseResponseCode.COURSE_GET_SUCCESS, result));
    }

    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<CreateCourseResponseDto>> createCourse(@LoginUser User user,
        @RequestBody CreateCourseRequestDto request) {

        CreateCourseResponseDto result = courseService.create(user, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(CourseResponseCode.COURSE_CREATED, result));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<DetailReadCourseResponseDto>> getDetailCourse(
        @LoginUser User user, @PathVariable Long courseId) {

        DetailReadCourseResponseDto result = courseService.getDetailCourse(user, courseId);
        return ResponseEntity.ok(
            ApiResponse.success(CourseResponseCode.COURSE_GET_SUCCESS, result));
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@LoginUser User user,
        @PathVariable Long courseId) {

        courseService.deleteCourse(user, courseId);
        return ResponseEntity.ok(
            ApiResponse.successWithoutData(CourseResponseCode.COURSE_DELETE_SUCCESS));
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<DetailReadCourseResponseDto>> updateCourse(
        @LoginUser User user,
        @PathVariable Long courseId, @RequestBody CreateCourseRequestDto request) {
        DetailReadCourseResponseDto result = courseService.updateCourse(user, courseId, request);
        return ResponseEntity.ok(
            ApiResponse.success(CourseResponseCode.COURSE_UPDATE_SUCCESS, result));
    }

    @GetMapping("/users/me/courses")
    public ResponseEntity<ApiResponse<CourseSimpleListWithTagResponseDto>> getWrittenCourses(
        @LoginUser User user, CourseSearchCond condition,
        @RequestParam(defaultValue = "ALL") MyCourseVisibility visibility) {
        CourseSimpleListWithTagResponseDto result = courseService.getWrittenCourses(user, condition,
            visibility);
        return ResponseEntity.ok(
            ApiResponse.success(CourseResponseCode.COURSE_GET_SUCCESS, result));
    }
}
