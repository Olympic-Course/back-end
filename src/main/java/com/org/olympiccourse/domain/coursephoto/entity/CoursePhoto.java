package com.org.olympiccourse.domain.coursephoto.entity;

import com.org.olympiccourse.domain.coursestep.entity.CourseStep;
import com.org.olympiccourse.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CoursePhoto extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private boolean isRep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_step_id", nullable = false)
    private CourseStep courseStep;

    public void setStep(CourseStep courseStep) {
        this.courseStep = courseStep;
    }
}
