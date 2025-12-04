package com.org.olympiccourse.domain.like.service;

import com.org.olympiccourse.domain.course.code.CourseResponseCode;
import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.course.repository.CourseRepository;
import com.org.olympiccourse.domain.like.dto.LikeProcessResponse;
import com.org.olympiccourse.domain.like.entity.CourseLike;
import com.org.olympiccourse.domain.like.repository.LikeRepository;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final CourseRepository courseRepository;

    public LikeProcessResponse likeProcess(Long courseId, User user) {

        CourseLike like = likeRepository.findByCourseIdAndUserId(courseId, user.getId());
        boolean liked;

        if (like == null) {
            Course findCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(
                    CourseResponseCode.COURSE_NOT_FOUND));

            likeRepository.save(CourseLike.builder()
                .user(user)
                .course(findCourse)
                .build());
            liked = true;
        } else {
            likeRepository.delete(like);
            liked = false;
        }

        Long courseLikeCount = likeRepository.countByCourseId(courseId);

        return new LikeProcessResponse(liked, courseLikeCount);
    }
}
