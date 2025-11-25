package com.org.olympiccourse.domain.user.service;

import com.org.olympiccourse.domain.user.entity.Role;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.repository.UserRepository;
import com.org.olympiccourse.domain.user.request.CheckDuplicationDto;
import com.org.olympiccourse.domain.user.request.Type;
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

        validateEmail(userJoinDto.getEmail());
        validateNickname(userJoinDto.getNickname());

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

    private void validateEmail(String email){
        if (userRepository.existsByEmailAndStatus(email, Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateNickname(String nickname){
        if (userRepository.existsByNicknameAndStatus(nickname,
            Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.NICKNAME_ALREADY_EXISTS);
        }
    }

    public void checkDuplication(CheckDuplicationDto checkDuplicationDto) {
        if(checkDuplicationDto.getType() == Type.EMAIL){
            validateEmail(checkDuplicationDto.getContent());
        }else if(checkDuplicationDto.getType() == Type.NICKNAME){
            validateNickname(checkDuplicationDto.getContent());
        }
    }
}
