package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

import com.example.hometest.User.User;

@Data
public class ResponseUserDto {
    private int errorCode;
    private String errorDescription;
    private String errorMessage;
    private List<User> response;
}
