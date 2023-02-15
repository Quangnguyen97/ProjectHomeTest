package com.example.hometest.User;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hometest.Account.*;
import com.example.hometest.Module.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        super();
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public User getUserByUserId(long UserId) {
        try {
            // Check error field
            if (ResourceValidString.StringIsError(String.valueOf(UserId))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "userId", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "userId",
                                String.valueOf(UserId)));
            }

            return userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceRuntimeException(
                            String.format("%s does not exist with field %s='%s'", "User", "userId",
                                    String.valueOf(UserId))));
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            // Check error field
            if (user == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with request body %s='%s'", "User", "User", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getUserId()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "userId", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getFullName()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "fullName", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getPassword()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "password", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getNotificationToken()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "notificationToken", "null"));
            }

            // Check data exists
            if (userRepository.findById(user.getUserId()).isEmpty() == false) {
                throw new ResourceRuntimeException(
                        String.format("%s have exist with field %s='%s'", "User", "userId",
                                String.valueOf(user.getUserId())));
            }

            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user, long UserId) {
        try {
            // Check error field
            if (user == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with request body %s='%s'", "User", "User", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(UserId))
                    || ResourceValidString.StringIsError(String.valueOf(user.getUserId()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "userId", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getFullName()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "fullName", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getPassword()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "password", "null"));
            } else if (ResourceValidString.StringIsError(String.valueOf(user.getNotificationToken()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "notificationToken", "null"));
            }

            // Check data exists
            if (UserId != user.getUserId()) {
                throw new ResourceRuntimeException(
                        String.format("%s is different request body with field %s:'%s', '%s'", "User", "userId",
                                String.valueOf(UserId), String.valueOf(user.getUserId())));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "userId",
                                String.valueOf(UserId)));
            }

            User existingUser = userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceRuntimeException(
                            String.format("%s does not exist with field %s='%s'", "User", "userId",
                                    String.valueOf(UserId))));
            existingUser.setFullName(user.getFullName());
            existingUser.setPassword(user.getPassword());
            existingUser.setNotificationToken(user.getNotificationToken());
            userRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteUser(long UserId) {
        try {
            // Check error field
            if (ResourceValidString.StringIsError(String.valueOf(UserId))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "User", "userId", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "userId",
                                String.valueOf(UserId)));
            }

            // Check account exists
            if (accountRepository.findByUserId(UserId).isEmpty() == false) {
                try {
                    accountRepository.deleteByUserId(UserId);
                } catch (Exception e) {
                    throw new ResourceRuntimeException(e.getMessage());
                }
            }

            userRepository.deleteById(UserId);
            return true;
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
