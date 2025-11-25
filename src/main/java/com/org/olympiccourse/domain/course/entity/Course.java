package com.org.olympiccourse.domain.course.entity;

import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Course extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titleKo;

    @Column(nullable = false)
    private String titleEn;

    @Column
    private String commentKo;

    @Column
    private String commentEn;

    @Column
    @Enumerated(EnumType.STRING)
    private Duration duration;

    @Column
    @Enumerated(EnumType.STRING)
    private Cost cost;

    @Column(nullable = false)
    private boolean secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
