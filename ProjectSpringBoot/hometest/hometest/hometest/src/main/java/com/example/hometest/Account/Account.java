package com.example.hometest.Account;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_Account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @NotNull
    @Min(value = 1)
    @Column(name = "user_id", insertable = true, updatable = true)
    private long userId;

    @NotNull
    @Min(value = 1)
    @Column(name = "account_number")
    private @Id @GeneratedValue long accountNumber;

    @NotNull
    @Column(name = "account_balance")
    private double accountBalance;

    public Account(long userId, long accountNumber, double accountBalance) {
        super();
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Account))
            return false;
        Account account = (Account) object;
        return Objects.equals(this.accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accountNumber);
    }
}
