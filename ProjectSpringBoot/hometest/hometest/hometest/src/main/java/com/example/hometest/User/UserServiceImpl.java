package com.example.hometest.User;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.hometest.Module.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User getUserByUserId(long userId) {
        try {
            return userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", userId));
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User updateUser(User user, long userId) {
        try {
            User existingUser = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", userId));

            existingUser.setFullName(user.getFullName());
            existingUser.setPassword(user.getPassword());
            existingUser.setNotificationToken(user.getNotificationToken());
            userRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", userId));
            userRepository.deleteById(userId);

        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
        }
    }
}
