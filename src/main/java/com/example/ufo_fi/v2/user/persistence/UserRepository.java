package com.example.ufo_fi.v2.user.persistence;

import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKakaoId(String string);

    Page<User> findAllByRole(Role role, PageRequest pageRequest);

    long countByRole(Role role);

    long countByRoleNot(Role role);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findByIdWithLock(Long id);
}
