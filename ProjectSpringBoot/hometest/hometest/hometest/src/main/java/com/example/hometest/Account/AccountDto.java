package com.example.hometest.Account;

import lombok.Data;

@Data
public class AccountDto {
    private Iterable<Long> UserId;
    private int AccountNumber;
    private double Balance;
}
