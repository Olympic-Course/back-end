package com.org.olympiccourse.domain.usercourse.service;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.course.repository.CourseRepository;
import com.org.olympiccourse.domain.coursestep.code.CourseStepResponseCode;
import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.domain.coursestep.repository.CourseStepRepository;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.usercourse.entity.UserCourse;
import com.org.olympiccourse.domain.usercourse.repository.UserCourseRepository;
import com.org.olympiccourse.domain.usercourse.request.CreateCourseMemoRequestDto;
import com.org.olympiccourse.domain.usercourse.request.MemoRequestDto;
import com.org.olympiccourse.domain.usercourse.response.CreateCourseMemoResponseDto;
import com.org.olympiccourse.domain.usercoursestep.entity.UserCourseStep;
import com.org.olympiccourse.domain.usercoursestep.repository.UserCourseStepRepository;
import com.org.olympiccourse.global.response.CustomException;
import java.util.ArrayList;
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

    public CreateCourseMemoResponseDto createMemo(User user, CreateCourseMemoRequestDto request,
        Long courseId) {

        Course findCourse = courseRepository.findById(courseId)
            .orElseThrow(() -> new CustomException(
                CourseResponseCode.COURSE_NOT_FOUND));

        if (findCourse.isSecret() && !Objects.equals(user.getId(), findCourse.getUser().getId())) {
            throw new CustomException(CourseResponseCode.COURSE_ACCESS_DENIED);
        }

        UserCourse saveUserCourse = UserCourse.builder()
            .user(user)
            .course(findCourse)
            .build();

        userCourseRepository.save(saveUserCourse);

        List<Long> stepIds = request.steps().stream()
            .map(MemoRequestDto::stepId)
            .toList();

        List<CourseStep> courseSteps = courseStepRepository.findAllById(stepIds);
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
                .userCourse(saveUserCourse)
                .courseStep(currentStep)
                .memo(dto.memo())
                .build());
        }
        userCourseStepRepository.saveAll(userCourseSteps);

        return new CreateCourseMemoResponseDto(saveUserCourse.getId());
    }
}
