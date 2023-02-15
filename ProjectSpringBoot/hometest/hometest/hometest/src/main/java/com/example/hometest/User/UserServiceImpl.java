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
    public User getUserByUserId(long UserId) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "UserId", "null"));
                return null;
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return null;
            }

            return userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "UserId", UserId));
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            // Check error param
            if (user == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "User", "null"));
                return null;
            } else if (String.valueOf(user.getUserId()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "UserId", "null"));
                return null;
            } else if (String.valueOf(user.getFullName()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "FullName", "null"));
                return null;
            } else if (String.valueOf(user.getPassword()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "Password", "null"));
                return null;
            } else if (String.valueOf(user.getNotificationToken()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "NotificationToken", "null"));
                return null;
            }

            // Check data exists
            if (userRepository.findById(user.getUserId()).isEmpty() == false) {
                new ResourceNotFoundException(
                        String.format("%s is exists with %s : '%s'", "User", "UserId",
                                String.valueOf(user.getUserId())));
                return null;
            }

            return userRepository.save(user);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User updateUser(User user, long UserId) {
        try {
            // Check error param
            if (user == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "User", "null"));
                return null;
            } else if (String.valueOf(UserId) == null || String.valueOf(user.getUserId()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "UserId", "null"));
                return null;
            } else if (String.valueOf(user.getFullName()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "FullName", "null"));
                return null;
            } else if (String.valueOf(user.getPassword()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "Password", "null"));
                return null;
            } else if (String.valueOf(user.getNotificationToken()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "User", "NotificationToken", "null"));
                return null;
            }

            // Check data exists
            if (UserId != user.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is different data with %s : '%s'", "User", "UserId",
                                String.valueOf(UserId) + " - " + String.valueOf(user.getUserId())));
                return null;
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return null;
            }

            User existingUser = userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "UserId", UserId));
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
    public boolean deleteUser(long UserId) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return false;
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return false;
            }

            userRepository.deleteById(UserId);
            return true;
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return false;
        }
    }
}
