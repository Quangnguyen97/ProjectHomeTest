package com.example.hometest.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findByUserId(long userId);

    Account findByAccountNumber(int accountNumber);

    void deleteByAccountNumber(int accountNumber);
}
