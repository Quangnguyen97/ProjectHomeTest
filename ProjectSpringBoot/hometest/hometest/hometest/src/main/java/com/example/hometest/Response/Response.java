package com.example.hometest.Response;

import java.util.Objects;
import java.util.List;

public class Response {
    private int errorCode;
    private String errorDescription;
    private List<object> response;

    public Response(long userId, String fullName, String password, String notificationToken) {
        super();
        this.userId = userId;
        this.fullName = fullName;
        this.password = password;
        this.notificationToken = notificationToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }
}
