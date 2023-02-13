package com.example.hometest.Account;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts(long UserId);

    Account getAccountByNumber(long UserId, int AccountNumber);

    Account saveAccount(long UserId, Account account);

    Account updateAccount(long UserId, Account account, int AccountNumber);

    void deleteAccount(long UserId, int AccountNumber);
}