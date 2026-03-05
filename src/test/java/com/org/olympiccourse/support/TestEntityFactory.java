package com.org.olympiccourse.support;

import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.coursephoto.entity.CoursePhoto;
import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.domain.like.entity.CourseLike;
import com.org.olympiccourse.domain.user.entity.Role;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.test.util.ReflectionTestUtils;

public class TestEntityFactory {

    private final EntityManager em;

    public TestEntityFactory(EntityManager em) {
        this.em = em;
    }

    public User user(String nickname) {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
            .nickname(nickname + uuid)
            .email(nickname + uuid + "@test.com")
            .password("password")
            .status(Status.ACTIVITY)
            .role(Role.ROLE_USER)
            .build();

        em.persist(user);
        return user;
    }

    public Course course(User user, String titleKo, boolean secret, LocalDateTime createdAt) {
        Course course = Course.builder()
            .titleKo(titleKo)
            .secret(secret)
            .user(user)
            .build();

        em.persist(course);

        ReflectionTestUtils.setField(course, "createdAt", createdAt);
        return course;
    }

    public CourseStep courseStep(Course course, int order) {
        CourseStep cs = CourseStep.builder()
            .stepOrder(order)
            .name("step " + order)
            .longitude(127.0)
            .latitude(37.0)
            .course(course)
            .build();

        em.persist(cs);
        return cs;
    }

    public CourseStep courseStepWithRepPhoto(Course course, int order) {
        CourseStep step = courseStep(course, order);

        CoursePhoto photo = CoursePhoto.builder()
            .path("/photo/course-step/" + UUID.randomUUID())
            .isRep(true)
            .build();

        photo.setStep(step);
        em.persist(photo);
        return step;
    }

    public CourseLike courseLike(Course course, User user) {
        CourseLike courseLike = CourseLike.builder()
            .course(course)
            .user(user)
            .build();

        em.persist(courseLike);
        return courseLike;
    }

    public void likes(Course course, int n) {
        for (int i = 0; i < n; i++) {
            User u = user("testnick" + i);
            courseLike(course, u);
        }
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

}
