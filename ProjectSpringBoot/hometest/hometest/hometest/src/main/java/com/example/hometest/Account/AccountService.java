package com.example.hometest.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts(long userId);

    Account getAccountByNumber(long userId, int accountNumber);

    Account saveAccount(long userId, Account account);

    Account updateAccount(long userId, Account account, int accountNumber);

    void deleteAccount(long userId, int accountNumber);
}