package com.org.olympiccourse.domain.course.service;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.course.repository.CourseRepository;
import com.org.olympiccourse.domain.course.request.CreateCourseRequestDto;
import com.org.olympiccourse.domain.course.response.CreateCourseResponseDto;
import com.org.olympiccourse.domain.course.response.DetailReadCourseResponseDto;
import com.org.olympiccourse.domain.course.response.StepResponseDto;
import com.org.olympiccourse.domain.coursephoto.entity.CoursePhoto;
import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.domain.coursestep.repository.CourseStepRepository;
import com.org.olympiccourse.domain.coursestep.request.StepRequestDto;
import com.org.olympiccourse.domain.like.repository.LikeRepository;
import com.org.olympiccourse.domain.tag.entity.CourseTag;
import com.org.olympiccourse.domain.tag.entity.Tag;
import com.org.olympiccourse.domain.tag.repository.CourseTagRepository;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.response.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseTagRepository courseTagRepository;
    private final CourseStepRepository courseStepRepository;
    private final LikeRepository likeRepository;

    public CreateCourseResponseDto create(User user, CreateCourseRequestDto request) {

        Course course = Course.builder()
            .user(user)
            .titleKo(request.title())
            .secret(request.secret())
            .commentKo(request.comment())
            .duration(request.duration())
            .cost(request.cost())
            .build();

        Course savedCourse = courseRepository.save(course);

        List<Tag> tagResponse = saveTag(request, savedCourse);

        List<StepResponseDto> srd = saveStepAndPhoto(
            request, savedCourse);

        return CreateCourseResponseDto.builder()
            .courseId(savedCourse.getId())
            .title(savedCourse.getTitleKo())
            .writer(savedCourse.getUser().getNickname())
            .comment(savedCourse.getCommentKo())
            .secret(savedCourse.isSecret())
            .tag(tagResponse)
            .steps(srd)
            .duration(savedCourse.getDuration())
            .cost(savedCourse.getCost())
            .build();
    }

    private List<StepResponseDto> saveStepAndPhoto(CreateCourseRequestDto request,
        Course savedCourse) {

        List<CourseStep> steps = new ArrayList<>();

        for (int i = 0; i < request.steps().size(); i++) {
            StepRequestDto stepReq = request.steps().get(i);

            CourseStep step = CourseStep.builder()
                .stepOrder(stepReq.stepOrder())
                .name(stepReq.name())
                .latitude(stepReq.latitude())
                .longitude(stepReq.longitude())
                .descriptionKo(stepReq.description())
                .course(savedCourse)
                .build();

            if (stepReq.photos() != null && !stepReq.photos().isEmpty()) {
                List<CoursePhoto> photos = stepReq.photos().stream()
                    .map(photoReq -> CoursePhoto.builder()
                        .path(photoReq.path())
                        .isRep(photoReq.isRep())
                        .build())
                    .toList();

                step.addPhotos(photos);
            }
            steps.add(step);
        }
        List<CourseStep> savedSteps = courseStepRepository.saveAll(steps);
        return savedSteps.stream()
            .map(StepResponseDto::from)
            .toList();
    }

    private List<Tag> saveTag(CreateCourseRequestDto request, Course savedCourse) {

        List<CourseTag> tag = new ArrayList<>();
        for (Tag t : request.tag()) {
            tag.add(CourseTag.builder()
                .course(savedCourse)
                .tag(t)
                .build());
        }

        List<CourseTag> savedTag = courseTagRepository.saveAll(tag);

        return toTagList(savedTag);
    }

    private List<Tag> toTagList(List<CourseTag> tags) {
        List<Tag> tagResponse = new ArrayList<>();
        for (CourseTag t : tags) {
            tagResponse.add(t.getTag());
        }
        return tagResponse;
    }

    public DetailReadCourseResponseDto getDetailCourse(User user, Long courseId) {

        Course findCourse = courseRepository.findById(courseId)
            .orElseThrow(() -> new CustomException(
                CourseResponseCode.COURSE_NOT_FOUND));

        // 비밀글 체크
        checkSecretCourse(user, findCourse);

        List<CourseStep> findCourseStep = courseStepRepository.findAllByCourseIdOrderByStepOrderAsc(
            findCourse.getId());
        List<StepResponseDto> stepResponseDtos = findCourseStep.stream()
            .map(StepResponseDto::from)
            .toList();
        List<CourseTag> findTag = courseTagRepository.findAllByCourseId(findCourse.getId());
        long likeNum = likeRepository.countByCourseId(courseId);
        boolean liked = false;
        if (user != null) {
            liked = likeRepository.existsByCourseIdAndUserId(courseId, user.getId());
        }

        return DetailReadCourseResponseDto.builder()
            .courseId(findCourse.getId())
            .title(findCourse.getTitleKo())
            .writer(findCourse.getUser().getNickname())
            .comment(findCourse.getCommentKo())
            .secret(findCourse.isSecret())
            .tag(toTagList(findTag))
            .steps(stepResponseDtos)
            .duration(findCourse.getDuration())
            .cost(findCourse.getCost())
            .liked(liked)
            .likeNum(likeNum)
            .build();
    }

    private void checkSecretCourse(User user, Course checkCourse) {

        if (checkCourse.isSecret()) {
            if (user == null || !Objects.equals(user.getId(), checkCourse.getUser().getId())) {
                throw new CustomException(CourseResponseCode.COURSE_ACCESS_DENIED);
            }
        }
    }
}
