package com.example.hometest.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(long UserId);

    Account findByUserIdAndAccountNumber(long UserId, long AccountNumber);

    void deleteByUserId(long UserId);
}
