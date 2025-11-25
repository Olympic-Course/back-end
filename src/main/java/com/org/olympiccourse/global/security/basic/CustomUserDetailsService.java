package com.org.olympiccourse.global.security.basic;

import com.org.olympiccourse.domain.user.code.UserResponseCode;
import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import com.org.olympiccourse.domain.user.repository.UserRepository;
import com.org.olympiccourse.global.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {

        User findUser = (userInfo.contains("@"))?userRepository.findByEmailAndStatus(userInfo, Status.ACTIVITY)
            .orElseThrow(()->new CustomException(UserResponseCode.USER_NOT_FOUND)):
        userRepository.findById(Long.parseLong(userInfo))
            .orElseThrow(()->new CustomException(UserResponseCode.USER_NOT_FOUND));

        if(findUser.getStatus() == Status.WITHDRAW){
            throw new DisabledException(UserResponseCode.WITHDRAWN_USER.getCode());
        }

        return new CustomUserDetails(findUser);
    }
}
