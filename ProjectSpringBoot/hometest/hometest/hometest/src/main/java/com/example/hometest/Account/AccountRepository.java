package com.example.hometest.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(int AccountNumber);

    void deleteByAccountNumber(int AccountNumber);
}
