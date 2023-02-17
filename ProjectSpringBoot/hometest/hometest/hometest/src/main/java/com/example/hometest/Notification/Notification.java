package com.example.hometest.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_Notification")
@Getter
@Setter
public class Notification {

    @NotNull
    @Min(value = 1)
    @Column(name = "notification_user_id")
    private long userId;

    @NotNull
    @NotEmpty(message = "fullName must not be empty")
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotEmpty(message = "notificationToken must not be empty")
    @Column(name = "notification_token")
    private String notificationToken;

    @NotNull
    @Min(value = 1)
    @Column(name = "account_number")
    private @Id long accountNumber;

    @NotNull
    @Column(name = "account_balance")
    private double accountBalance;

    public Notification() {
    }

    public Notification(long userId, String fullName, String notificationToken, long accountNumber,
            double accountBalance) {
        super();
        this.userId = userId;
        this.fullName = fullName;
        this.notificationToken = notificationToken;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
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

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
