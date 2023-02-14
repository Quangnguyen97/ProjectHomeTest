package com.example.hometest.User;

import lombok.Data;

@Data
public class UserDto {
    private long userId;
    private String fullName;
    private String password;
    private String notificationToken;
}
