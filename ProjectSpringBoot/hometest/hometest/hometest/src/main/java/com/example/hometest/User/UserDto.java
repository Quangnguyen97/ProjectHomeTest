package com.example.hometest.User;

import com.example.hometest.Account.*;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @JsonProperty("id")
    private long userId;

    @NotNull
    @NotEmpty(message = "fullName must not be empty")
    @JsonProperty("fullName")
    private String fullName;

    @NotNull
    @NotEmpty(message = "password must not be empty")
    @JsonProperty("password")
    private String password;

    @NotNull
    @NotEmpty(message = "notificationToken must not be empty")
    @JsonProperty("notificationToken")
    private String notificationToken;
}
