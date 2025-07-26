package com.example.ufo_fi.domain.user.repository;

import com.example.ufo_fi.domain.user.entity.Role;
import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKakaoId(String string);

    List<User> findAllByRole(Role role);

    long countByRole(Role role);

    long countByRoleNot(Role role);
}
