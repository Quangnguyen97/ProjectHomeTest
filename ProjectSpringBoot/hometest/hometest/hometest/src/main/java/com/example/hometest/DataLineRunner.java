package com.example.hometest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import com.example.hometest.User.*;
import com.example.hometest.Account.*;
import com.example.hometest.Module.*;

@Component
public class DataLineRunner implements CommandLineRunner {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    public DataLineRunner(UserServiceImpl userServiceImpl, AccountServiceImpl accountServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            userServiceImpl.saveUser(new User(1, "NGUYEN VAN A", "hash1", "token1"));
            userServiceImpl.saveUser(new User(2, "NGUYEN VAN B", "hash2", "token2"));
            userServiceImpl.saveUser(new User(3, "NGUYEN VAN C", "hash3", "token3"));

            accountServiceImpl.saveAccount(1, new Account(1, 10, 100));
            accountServiceImpl.saveAccount(2, new Account(2, 20, 200));
            accountServiceImpl.saveAccount(3, new Account(3, 30, 300));
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
