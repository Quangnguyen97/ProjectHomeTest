package com.example.hometest.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByUserId(long UserId);

    User saveUser(User User);

    User updateUser(User User, long UserId);

    void deleteUser(long UserId);
}