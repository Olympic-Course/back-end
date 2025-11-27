package com.org.olympiccourse.domain.coursestep.repository;

import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStepRepository extends JpaRepository<CourseStep, Long> {

    List<CourseStep> findAllByCourseIdOrderByStepOrderAsc(Long id);

    void deleteByCourseId(Long courseId);
}
