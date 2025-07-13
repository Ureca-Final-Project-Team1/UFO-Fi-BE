package com.example.ufo_fi.domain.useraccount.repository;

import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.useraccount.entity.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByUser(User user);

    Optional<UserAccount> findByUser(User user);
}
