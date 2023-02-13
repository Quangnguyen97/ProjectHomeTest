package com.example.hometest.Account;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Account {
    private @GeneratedValue long UserId;
    private @Id @GeneratedValue int AccountNumber;
    private double Balance;

    public Account(long UserId, int AccountNumber, double Balance) {
        super();
        this.UserId = UserId;
        this.AccountNumber = AccountNumber;
        this.Balance = Balance;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long UserId) {
        this.UserId = UserId;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Account))
            return false;
        Account account = (Account) object;
        return Objects.equals(this.AccountNumber, account.AccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.AccountNumber);
    }
}
