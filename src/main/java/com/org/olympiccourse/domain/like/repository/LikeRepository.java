package com.org.olympiccourse.domain.like.repository;

import com.org.olympiccourse.domain.like.entity.CourseLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<CourseLike, Long> {

    long countByCourseId(Long courseId);

    boolean existsByCourseIdAndUserId(Long courseId, Long id);
}
