package com.example.ufo_fi.domain.user;

import com.example.ufo_fi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
