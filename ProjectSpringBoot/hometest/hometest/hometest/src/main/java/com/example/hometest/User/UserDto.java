package com.example.hometest.User;

import com.example.hometest.Account.*;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @JsonProperty("id")
    private long userId;

    @NotNull
    @JsonProperty("fullName")
    private String fullName;

    @NotNull
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("notificationToken")
    private String notificationToken;

    @JsonProperty("account")
    private Set<AccountDto> account;
}
