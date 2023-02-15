package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

import com.example.hometest.Account.Account;

@Data
public class ResponseAccountDto {
    private int errorCode;
    private String errorDescription;
    private List<Account> response;
}
