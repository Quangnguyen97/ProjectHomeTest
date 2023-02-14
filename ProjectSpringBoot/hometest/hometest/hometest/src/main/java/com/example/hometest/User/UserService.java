package com.example.hometest.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByUserId(long UserId);

    User saveUser(User user);

    User updateUser(User user, long UserId);

    boolean deleteUser(long UserId);
}