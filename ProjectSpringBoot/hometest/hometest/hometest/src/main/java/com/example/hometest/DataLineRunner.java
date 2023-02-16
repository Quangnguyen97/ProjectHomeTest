package com.example.hometest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hometest.User.*;
import com.example.hometest.Account.*;
import com.example.hometest.Module.*;

@Component
public class DataLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            userRepository.save(new User(1, "NGUYEN VAN A", "hash1", "token1"));
            userRepository.save(new User(2, "NGUYEN VAN B", "hash2", "token2"));
            userRepository.save(new User(3, "NGUYEN VAN C", "hash3", "token3"));

            accountRepository.save(new Account(1, 10, 100));
            accountRepository.save(new Account(2, 20, 200));
            accountRepository.save(new Account(3, 30, 300));
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
