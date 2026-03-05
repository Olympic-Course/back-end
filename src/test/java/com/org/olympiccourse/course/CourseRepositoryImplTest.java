package com.org.olympiccourse.course;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.olympiccourse.domain.course.entity.Course;
import com.org.olympiccourse.domain.course.repository.CourseCustomRepository;
import com.org.olympiccourse.domain.course.repository.CourseCustomRepositoryImpl;
import com.org.olympiccourse.domain.course.response.CourseOverviewResponseDto;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.config.QuerydslConfig;
import com.org.olympiccourse.support.TestEntityFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QuerydslConfig.class, CourseCustomRepositoryImpl.class})
class CourseRepositoryImplTest {

    @Autowired
    EntityManager em;
    @Autowired
    CourseCustomRepository courseRepository;

    TestEntityFactory f;

    @BeforeEach
    void setUp() {
        f = new TestEntityFactory(em);
    }

    @Test
    void findBestThreeCourses_ordersByLikeCount_andSetsLiked_andFiltersThisMonth() {
        // given
        LocalDate now = LocalDate.of(2026, 3, 4);
        LocalDateTime thisMonth = now.withDayOfMonth(2).atStartOfDay();
        LocalDateTime lastMonth = now.minusMonths(1).withDayOfMonth(15).atStartOfDay();

        User owner = f.user("owner");
        User me = f.user("me");

        // 이번 달 공개 코스 4개
        Course c1 = f.course(owner, "c1", false, thisMonth);
        f.courseStepWithRepPhoto(c1, 1);
        f.likes(c1, 5);
        f.courseLike(c1, me);

        Course c2 = f.course(owner,  "c2", false, thisMonth.plusDays(1));
        f.courseStepWithRepPhoto(c2, 1);
        f.likes(c2, 3);

        Course c3 = f.course(owner, "c3", false, thisMonth.plusDays(2));
        f.courseStepWithRepPhoto(c3, 1);
        f.likes(c3, 1);

        Course c4 = f.course(owner, "c4", false, thisMonth.plusDays(3));
        f.courseStepWithRepPhoto(c4, 1);

        // 지난달 코스
        Course old = f.course(owner, "old", false, lastMonth);
        f.courseStepWithRepPhoto(old, 1);
        f.likes(old, 100);

        // 비공개 코스
        Course secret = f.course(owner, "secret", true, thisMonth);
        f.courseStepWithRepPhoto(secret, 1);
        f.likes(secret, 100);

        f.flushAndClear();

        // when
        List<CourseOverviewResponseDto> result =
            courseRepository.findBestThreeCourses(me.getId(), now);

        // then
        assertThat(result).hasSize(3);

        // 좋아요 순
        assertThat(result.get(0).getCourseId()).isEqualTo(c1.getId());
        assertThat(result.get(1).getCourseId()).isEqualTo(c2.getId());
        assertThat(result.get(2).getCourseId()).isEqualTo(c3.getId());

        // 사용자의 좋아요 여부
        assertThat(result.get(0).getLiked()).isTrue();
        assertThat(result.get(1).getLiked()).isFalse();
        assertThat(result.get(2).getLiked()).isFalse();
    }
}
