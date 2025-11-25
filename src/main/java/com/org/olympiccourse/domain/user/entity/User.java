package com.org.olympiccourse.domain.user.entity;

import com.org.olympiccourse.domain.user.request.UserUpdateRequestDto;
import com.org.olympiccourse.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.ACTIVITY;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void withdraw() {
        this.status = Status.WITHDRAW;
    }

    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        String newNickname = userUpdateRequestDto.getNickname();
        if (newNickname != null && !newNickname.isEmpty()) {
            this.nickname = newNickname;
        }

        Language newLanguage = userUpdateRequestDto.getLanguage();
        if (newLanguage != null) {
            this.language = newLanguage;
        }
    }
}
