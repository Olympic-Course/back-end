package com.org.olympiccourse.domain.user.repository;

import com.org.olympiccourse.domain.user.entity.Status;
import com.org.olympiccourse.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndStatus(String email, Status status);
    boolean existsByNicknameAndStatus(String nickname, Status status);
    Optional<User> findByEmailAndStatus(String email, Status status);
    Optional<User> findByNicknameAndStatus(String nickname, Status status);
    Optional<User> findById(Long id);
}
