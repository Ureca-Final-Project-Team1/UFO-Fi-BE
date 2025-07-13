package com.example.ufo_fi.domain.user.entity;

import com.example.ufo_fi.domain.user.dto.request.AccountCreateReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_accounts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank", length = 255)
    private String bank;

    @Column(name = "bank_account", length = 255)
    private String bankAccount;

    @Column(name = "password", length = 255)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserAccount of(final User user, final AccountCreateReq accountCreateReq) {
        return UserAccount.builder()
                .bank(accountCreateReq.getBank())
                .bankAccount(accountCreateReq.getBankAccount())
                .password(accountCreateReq.getPassword())
                .user(user)
                .build();
    }
}
