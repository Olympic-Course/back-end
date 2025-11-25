package com.org.olympiccourse.domain.user.service;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.entity.Role;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.repository.UserRepository;
import com.org.olympiccourse.domain.user.request.CheckDuplicationRequestDto;
import com.org.olympiccourse.domain.user.request.Type;
import com.org.olympiccourse.domain.user.request.UserJoinRequestDto;
import com.org.olympiccourse.global.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserJoinRequestDto userJoinRequestDto){

        validateEmail(userJoinRequestDto.getEmail());
        validateNickname(userJoinRequestDto.getNickname());

        User saveUser = User.builder()
            .email(userJoinRequestDto.getEmail())
            .nickname(userJoinRequestDto.getNickname())
            .password(passwordEncoder.encode(userJoinRequestDto.getPassword()))
            .language(userJoinRequestDto.getLanguage())
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

    public void checkDuplication(CheckDuplicationRequestDto checkDuplicationRequestDto) {
        if(checkDuplicationRequestDto.getType() == Type.EMAIL){
            validateEmail(checkDuplicationRequestDto.getContent());
        }else if(checkDuplicationRequestDto.getType() == Type.NICKNAME){
            validateNickname(checkDuplicationRequestDto.getContent());
        }
    }

    public void withdraw(User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(()->new CustomException(UserResponseCode.USER_NOT_FOUND));
        findUser.withdraw();
    }
}
