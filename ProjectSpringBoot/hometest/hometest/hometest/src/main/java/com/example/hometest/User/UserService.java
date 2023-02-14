package com.example.hometest.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByUserId(long userId);

    User saveUser(User user);

    User updateUser(User user, long userId);

    void deleteUser(long userId);
}