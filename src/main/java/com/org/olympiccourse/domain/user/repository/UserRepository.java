package com.org.olympiccourse.domain.user.repository;

import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndStatus(String email, Status status);
    boolean existsByNicknameAndStatus(String nickname, Status status);
}
