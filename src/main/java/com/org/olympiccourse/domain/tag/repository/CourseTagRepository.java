package com.org.olympiccourse.domain.tag.repository;

import com.org.olympiccourse.domain.tag.entity.CourseTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {

    List<CourseTag> findAllByCourseId(Long id);
}
