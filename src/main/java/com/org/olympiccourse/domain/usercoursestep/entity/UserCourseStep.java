package com.org.olympiccourse.domain.usercoursestep.entity;

import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.domain.usercourse.entity.UserCourse;
import com.org.olympiccourse.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_course_step_course_tag",
            columnNames = {"user_course_id", "course_step_id"}
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserCourseStep extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id", nullable = false)
    private UserCourse userCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_step_id", nullable = false)
    private CourseStep courseStep;
}
