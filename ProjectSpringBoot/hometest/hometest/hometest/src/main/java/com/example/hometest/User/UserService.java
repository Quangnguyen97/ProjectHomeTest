package com.example.hometest.User;

import java.util.List;

public interface UserService {
    User saveUser(User User);

    List<User> getAllUsers();

    User getUserByUserId(long UserId);

    User updateUser(User User, long UserId);

    void deleteUser(long UserId);
}