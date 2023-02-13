package com.example.hometest.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts(Iterable<Long> UserId);

    Account getAccountByNumber(Iterable<Long> UserId, int AccountNumber);

    Account saveAccount(Iterable<Long> UserId, Account account);

    Account updateAccount(Iterable<Long> UserId, Account account, int AccountNumber);

    void deleteAccount(Iterable<Long> UserId, int AccountNumber);
}