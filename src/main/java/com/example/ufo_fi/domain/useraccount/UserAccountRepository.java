package com.example.ufo_fi.domain.useraccount;

import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.useraccount.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByUser(User user);
}
