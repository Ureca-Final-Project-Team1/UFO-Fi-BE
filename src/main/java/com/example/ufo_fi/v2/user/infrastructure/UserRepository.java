package com.example.ufo_fi.v2.user.infrastructure;

import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKakaoId(String string);

    List<User> findAllByRole(Role role);

    long countByRole(Role role);

    long countByRoleNot(Role role);
}
