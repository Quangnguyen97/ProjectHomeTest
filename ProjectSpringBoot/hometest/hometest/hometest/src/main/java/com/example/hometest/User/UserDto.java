package com.example.hometest.User;

import lombok.Data;

@Data
public class UserDto {
    private long UserId;
    private String FullName;
    private String Password;
    private String NotificationToken;
}
