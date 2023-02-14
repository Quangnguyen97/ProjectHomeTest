package com.example.hometest.Account;

import lombok.Data;

@Data
public class AccountDto {
    private long userId;
    private long accountNumber;
    private double balance;
}
