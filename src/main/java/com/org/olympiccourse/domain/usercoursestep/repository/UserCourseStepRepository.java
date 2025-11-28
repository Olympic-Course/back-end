package com.org.olympiccourse.domain.usercoursestep.repository;

import com.org.olympiccourse.domain.usercourse.entity.UserCourse;
import com.org.olympiccourse.domain.usercoursestep.entity.UserCourseStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserCourseStepRepository extends JpaRepository<UserCourseStep, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from UserCourseStep u where u.userCourse = :userCourse")
    int deleteByUserCourse(UserCourse userCourse);

}
