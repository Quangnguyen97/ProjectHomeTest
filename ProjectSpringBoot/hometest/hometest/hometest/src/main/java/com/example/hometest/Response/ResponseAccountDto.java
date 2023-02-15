package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

import com.example.hometest.Account.Account;

@Data
public class ResponseAccountDto {
    private int status;
    private String description;
    private String message;
    private List<Account> response;
}
