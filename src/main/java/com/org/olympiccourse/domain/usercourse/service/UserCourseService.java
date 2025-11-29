package com.org.olympiccourse.domain.usercourse.service;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.course.repository.CourseRepository;
import com.org.olympiccourse.domain.coursestep.code.CourseStepResponseCode;
import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.domain.coursestep.repository.CourseStepRepository;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.usercourse.code.UserCourseResponseCode;
import com.org.olympiccourse.domain.usercourse.entity.UserCourse;
import com.org.olympiccourse.domain.usercourse.repository.UserCourseRepository;
import com.org.olympiccourse.domain.usercourse.request.CreateCourseMemoRequestDto;
import com.org.olympiccourse.domain.usercourse.request.MemoRequestDto;
import com.org.olympiccourse.domain.usercourse.response.CreateCourseMemoResponseDto;
import com.org.olympiccourse.domain.usercourse.response.DetailUserStepResponseDto;
import com.org.olympiccourse.domain.usercourse.response.UserStepsResponseDto;
import com.org.olympiccourse.domain.usercoursestep.entity.UserCourseStep;
import com.org.olympiccourse.domain.usercoursestep.repository.UserCourseStepRepository;
import com.org.olympiccourse.global.response.CustomException;
import com.org.olympiccourse.global.response.GlobalErrorCode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCourseService {

    private final CourseRepository courseRepository;
    private final CourseStepRepository courseStepRepository;
    private final UserCourseRepository userCourseRepository;
    private final UserCourseStepRepository userCourseStepRepository;

    public UserStepsResponseDto createMemo(User user, CreateCourseMemoRequestDto request,
        Long courseId) {

        Course findCourse = validCourse(user.getId(), courseId);

        UserCourse saveUserCourse = UserCourse.builder()
            .user(user)
            .course(findCourse)
            .build();

        userCourseRepository.save(saveUserCourse);

        List<CourseStep> courseSteps = courseStepRepository.findAllById(getStepIds(request));
        List<UserCourseStep> userCourseSteps = getUserCourseSteps(
            request, courseId, courseSteps, saveUserCourse);
        userCourseStepRepository.saveAll(userCourseSteps);

        return new UserStepsResponseDto(saveUserCourse.getId(), getDetailUserStepResponseDtoList(
            userCourseSteps));
    }

    public UserStepsResponseDto updateMemo(User user, CreateCourseMemoRequestDto request,
        Long courseId, Long userCourseId) {

        UserCourse userCourse = userCourseRepository.findById(userCourseId)
            .orElseThrow(() -> new CustomException(
                UserCourseResponseCode.USER_COURSE_NOT_FOUND));

        if (!Objects.equals(userCourse.getUser().getId(), user.getId()) ||
            !Objects.equals(userCourse.getCourse().getId(), courseId)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        userCourseStepRepository.deleteByUserCourse(userCourse);

        List<CourseStep> courseSteps = courseStepRepository.findAllById(getStepIds(request));
        List<UserCourseStep> userCourseSteps = getUserCourseSteps(
            request, courseId, courseSteps, userCourse);

        List<UserCourseStep> savedSteps = userCourseStepRepository.saveAll(userCourseSteps);

        return new UserStepsResponseDto(userCourseId, getDetailUserStepResponseDtoList(
            savedSteps));
    }

    private List<DetailUserStepResponseDto> getDetailUserStepResponseDtoList(
        List<UserCourseStep> savedSteps) {
        List<DetailUserStepResponseDto> detailUserStepResponseDtos = savedSteps.stream().map(
                ucs -> DetailUserStepResponseDto.builder()
                    .stepId(ucs.getCourseStep().getId())
                    .name(ucs.getCourseStep().getName())
                    .stepOrder(ucs.getCourseStep().getStepOrder())
                    .latitude(ucs.getCourseStep().getLatitude())
                    .longitude(ucs.getCourseStep().getLongitude())
                    .memo(ucs.getMemo())
                    .build())
            .sorted(Comparator.comparing(DetailUserStepResponseDto::stepOrder))
            .toList();
        return detailUserStepResponseDtos;
    }

    private List<UserCourseStep> getUserCourseSteps(CreateCourseMemoRequestDto request,
        Long courseId, List<CourseStep> courseSteps, UserCourse userCourse) {

        Map<Long, CourseStep> stepMap = courseSteps.stream()
            .collect(Collectors.toMap(CourseStep::getId, cs -> cs));

        List<UserCourseStep> userCourseSteps = new ArrayList<>();
        for (MemoRequestDto dto : request.steps()) {

            CourseStep currentStep = stepMap.get(dto.stepId());
            if (currentStep == null) {
                throw new CustomException(CourseStepResponseCode.COURSE_STEP_NOT_FOUND);
            }

            if (!Objects.equals(currentStep.getCourse().getId(), courseId)) {
                throw new CustomException(CourseStepResponseCode.COURSE_STEP_NOT_BELONG_TO_COURSE);
            }

            userCourseSteps.add(UserCourseStep.builder()
                .userCourse(userCourse)
                .courseStep(currentStep)
                .memo(dto.memo())
                .build());
        }
        return userCourseSteps;
    }

    private List<Long> getStepIds(CreateCourseMemoRequestDto request) {
        List<Long> stepIds = request.steps().stream()
            .map(MemoRequestDto::stepId)
            .toList();
        return stepIds;
    }

    private Course validCourse(Long userId, Long courseId) {
        Course findCourse = courseRepository.findById(courseId)
            .orElseThrow(() -> new CustomException(
                CourseResponseCode.COURSE_NOT_FOUND));

        if (findCourse.isSecret() && !Objects.equals(userId, findCourse.getUser().getId())) {
            throw new CustomException(CourseResponseCode.COURSE_ACCESS_DENIED);
        }

        return findCourse;
    }
}
