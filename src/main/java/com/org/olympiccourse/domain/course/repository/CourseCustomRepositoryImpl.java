package com.org.olympiccourse.domain.course.repository;

import static com.org.olympiccourse.domain.course.entity.QCourse.course;
import static com.org.olympiccourse.domain.coursephoto.entity.QCoursePhoto.coursePhoto;
import static com.org.olympiccourse.domain.coursestep.entity.QCourseStep.courseStep;
import static com.org.olympiccourse.domain.like.entity.QCourseLike.courseLike;
import static com.org.olympiccourse.domain.tag.entity.QCourseTag.courseTag;

import com.org.olympiccourse.domain.course.request.CourseSearchCond;
import com.org.olympiccourse.domain.course.request.MyCourseVisibility;
import com.org.olympiccourse.domain.course.response.CourseOverviewResponseDto;
import com.org.olympiccourse.domain.course.response.CourseOverviewTagResponseDto;
import com.org.olympiccourse.domain.tag.entity.Tag;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseCustomRepositoryImpl implements CourseCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<CourseOverviewResponseDto> findBestThreeCourses(Long userId) {

        NumberExpression likeCount = getLikeCount();

        BooleanExpression likedExpr = userId == null ? Expressions.FALSE :
            Expressions.booleanTemplate(
                "sum(case when {0} = {1} then 1 else 0 end) > 0",
                courseLike.user.id, userId
            );

        return jpaQueryFactory.select(Projections.constructor(CourseOverviewResponseDto.class,
                course.id, coursePhoto.path.max(), course.titleKo, course.user.nickname, likeCount,
                likedExpr))
            .from(course)
            .join(courseStep).on(courseStep.course.eq(course))
            .join(coursePhoto).on(coursePhoto.courseStep.eq(courseStep)
                .and(coursePhoto.isRep.isTrue()))
            .leftJoin(courseLike).on(courseLike.course.eq(course))
            .where(course.secret.isFalse())
            .groupBy(course.id)
            .orderBy(likeCount.desc(), course.id.desc())
            .limit(3)
            .fetch();
    }

    @Override
    public List<CourseOverviewResponseDto> searchCourseList(Long userId, CourseSearchCond condition,
        int size) {
        return jpaQueryFactory.select(Projections.constructor(CourseOverviewResponseDto.class,
                course.id, coursePhoto.path.max(), course.titleKo, course.user.nickname, getLikeCount(),
                getLikedExpr(userId)))
            .from(course)
            .join(courseStep).on(courseStep.course.eq(course))
            .join(coursePhoto).on(coursePhoto.courseStep.eq(courseStep)
                .and(coursePhoto.isRep.isTrue()))
            .leftJoin(courseLike).on(courseLike.course.eq(course))
            .leftJoin(courseTag).on(courseTag.course.eq(course))
            .where(
                keywordCond(condition.keyword()),
                tagsCond(condition.tags()),
                cursorCond(condition.cursor()),
                course.secret.isFalse()
            )
            .groupBy(course.id)
            .orderBy(course.id.desc())
            .limit(size + 1)
            .fetch();
    }

    private BooleanExpression getLikedExpr(Long userId) {
        BooleanExpression likedExpr = (userId == null) ? Expressions.FALSE :
            Expressions.booleanTemplate(
                "sum(case when {0} = {1} then 1 else 0 end) > 0",
                courseLike.user.id, userId
            );
        return likedExpr;
    }

    private NumberExpression getLikeCount() {
        NumberExpression likeCount = courseLike.id.countDistinct();
        return likeCount;
    }

    @Override
    public List<CourseOverviewTagResponseDto> findByUserIdWithSearchCond(Long userId,
        CourseSearchCond condition, MyCourseVisibility visibility, int size) {
        return jpaQueryFactory.select(Projections.constructor(CourseOverviewTagResponseDto.class,
                course.id, coursePhoto.path.max(), course.titleKo, course.user.nickname, getLikeCount(),
                getLikedExpr(userId)))
            .from(course)
            .join(courseStep).on(courseStep.course.eq(course))
            .join(coursePhoto).on(coursePhoto.courseStep.eq(courseStep)
                .and(coursePhoto.isRep.isTrue()))
            .leftJoin(courseLike).on(courseLike.course.eq(course))
            .leftJoin(courseTag).on(courseTag.course.eq(course))
            .where(
                keywordCond(condition.keyword()),
                tagsCond(condition.tags()),
                cursorCond(condition.cursor()),
                myCourseCond(userId),
                visibilityCond(visibility)
            )
            .groupBy(course.id)
            .orderBy(course.id.desc())
            .limit(size + 1)
            .fetch();
    }

    private BooleanExpression myCourseCond(Long userId) {
        return course.user.id.eq(userId);
    }

    private BooleanExpression visibilityCond(MyCourseVisibility visibility) {
        if (visibility == null || visibility == MyCourseVisibility.ALL) {
            return null;
        }
        if (visibility == MyCourseVisibility.PUBLIC) {
            return course.secret.isFalse();
        }
        if (visibility == MyCourseVisibility.SECRET) {
            return course.secret.isTrue();
        }
        return null;
    }

    private BooleanExpression keywordCond(String keyword) {
        return (keyword != null && !keyword.isBlank())
            ? course.titleKo.contains(keyword)
            : null;
    }

    private BooleanExpression tagsCond(List<Tag> tags) {
        return (tags != null && !tags.isEmpty())
            ? courseTag.tag.in(tags)
            : null;
    }

    private BooleanExpression cursorCond(Long cursor) {
        return cursor != null
            ? course.id.lt(cursor)
            : null;
    }
}
