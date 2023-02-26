package com.example.hometest.Account;

import java.util.List;

public interface AccountService {

    List<Account> getByUserId(long userId);

    Account getByUserIdAndAccountNumber(long userId, long accountNumber);

    Account saveAccount(long userId, Account account);

    Account updateAccount(long userId, Account account, long accountNumber);

    boolean deleteAccount(long userId, long accountNumber);
}