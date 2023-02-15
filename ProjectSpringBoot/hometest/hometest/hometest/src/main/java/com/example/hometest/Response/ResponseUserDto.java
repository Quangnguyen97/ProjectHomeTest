package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

import com.example.hometest.User.User;

@Data
public class ResponseUserDto {
    private int status;
    private String description;
    private String message;
    private List<User> response;
}
