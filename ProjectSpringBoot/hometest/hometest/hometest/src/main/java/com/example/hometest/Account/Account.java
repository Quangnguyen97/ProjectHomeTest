package com.example.hometest.Account;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @NotEmpty(message = "userId must not be empty")

    private @GeneratedValue(strategy = GenerationType.AUTO) long userId;
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long accountNumber;

    @NotEmpty(message = "balance must not be empty")
    private double balance;

    public Account(long userId, long accountNumber, double balance) {
        super();
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
