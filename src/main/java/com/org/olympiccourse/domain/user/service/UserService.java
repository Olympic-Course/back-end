package com.org.olympiccourse.domain.user.service;

import com.org.olympiccourse.domain.user.entity.Role;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.repository.UserRepository;
import com.org.olympiccourse.domain.user.request.UserJoinDto;
import com.org.olympiccourse.global.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(UserJoinDto userJoinDto){

        validateEmailAndNickname(userJoinDto.getEmail(), userJoinDto.getNickname());

        User saveUser = User.builder()
            .email(userJoinDto.getEmail())
            .nickname(userJoinDto.getNickname())
            .password(userJoinDto.getPassword())
            .language(userJoinDto.getLanguage())
            .role(Role.ROLE_USER)
            .status(Status.ACTIVITY)
            .build();

        userRepository.save(saveUser);
    }

    private void validateEmailAndNickname(String email, String nickname) {

        if (userRepository.existsByEmailAndStatus(email, Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByNicknameAndStatus(nickname,
            Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.NICKNAME_ALREADY_EXISTS);
        }
    }
}
