package com.org.olympiccourse.domain.user.service;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.entity.Role;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.repository.UserRepository;
import com.org.olympiccourse.domain.user.request.CheckDuplicationRequestDto;
import com.org.olympiccourse.domain.user.request.NewPasswordRequestDto;
import com.org.olympiccourse.domain.user.request.PasswordCheckRequestDto;
import com.org.olympiccourse.domain.user.request.Type;
import com.org.olympiccourse.domain.user.request.UserJoinRequestDto;
import com.org.olympiccourse.domain.user.request.UserUpdateRequestDto;
import com.org.olympiccourse.domain.user.response.BasicUserInfoResponse;
import com.org.olympiccourse.global.response.CustomException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String CHECK_PASSWORD_PREFIX = "check-password:";

    public void join(UserJoinRequestDto userJoinRequestDto) {

        validateEmail(userJoinRequestDto.getEmail());
        validateNickname(userJoinRequestDto.getNickname());

        User saveUser = User.builder()
            .email(userJoinRequestDto.getEmail())
            .nickname(userJoinRequestDto.getNickname())
            .password(passwordEncoder.encode(userJoinRequestDto.getPassword()))
            .role(Role.ROLE_USER)
            .status(Status.ACTIVITY)
            .build();

        userRepository.save(saveUser);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmailAndStatus(email, Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateNickname(String nickname) {
        if (userRepository.existsByNicknameAndStatus(nickname,
            Status.ACTIVITY)) {
            throw new CustomException(UserResponseCode.NICKNAME_ALREADY_EXISTS);
        }
    }

    public void checkDuplication(CheckDuplicationRequestDto checkDuplicationRequestDto) {
        if (checkDuplicationRequestDto.getType() == Type.EMAIL) {
            validateEmail(checkDuplicationRequestDto.getContent());
        } else if (checkDuplicationRequestDto.getType() == Type.NICKNAME) {
            validateNickname(checkDuplicationRequestDto.getContent());
        }
    }

    public void withdraw(User user) {
        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new CustomException(UserResponseCode.USER_NOT_FOUND));
        findUser.withdraw();
    }

    public BasicUserInfoResponse update(User user, UserUpdateRequestDto userUpdateRequestDto) {

        User updateUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new CustomException(UserResponseCode.USER_NOT_FOUND));
        validateNickname(userUpdateRequestDto.getNickname());
        updateUser.update(userUpdateRequestDto);

        return BasicUserInfoResponse.builder()
            .userId(updateUser.getId())
            .email(updateUser.getEmail())
            .nickname(updateUser.getNickname())
            .build();
    }

    public BasicUserInfoResponse getUserInfo(User user) {

        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new CustomException(UserResponseCode.USER_NOT_FOUND));

        return BasicUserInfoResponse.builder()
            .userId(findUser.getId())
            .email(findUser.getEmail())
            .nickname(findUser.getNickname())
            .build();
    }

    public void checkCurPassword(User user, PasswordCheckRequestDto passwordCheckRequestDto) {

        if (passwordEncoder.matches(passwordCheckRequestDto.curPassword(), user.getPassword())) {
            redisTemplate.opsForValue()
                .set(CHECK_PASSWORD_PREFIX + user.getId(), "checked", Duration.ofMinutes(5));
        } else {
            throw new CustomException(UserResponseCode.NOT_MATCHED_PASSWORD);
        }
    }

    public void changePassword(User user, NewPasswordRequestDto request) {

        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new CustomException(UserResponseCode.USER_NOT_FOUND));
        if (redisTemplate.hasKey(CHECK_PASSWORD_PREFIX + user.getId())) {
            redisTemplate.delete(CHECK_PASSWORD_PREFIX + user.getId());
            findUser.changePassword(passwordEncoder.encode(request.newPassword()));
        } else {
            throw new CustomException(UserResponseCode.NEED_PASSWORD_CHECK);
        }
    }
}
