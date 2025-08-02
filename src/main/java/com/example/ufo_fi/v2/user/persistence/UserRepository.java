package com.example.ufo_fi.v2.user.persistence;

import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKakaoId(String string);

    Page<User> findAllByRole(Role role, PageRequest pageRequest);

    long countByRole(Role role);

    long countByRoleNot(Role role);
}
