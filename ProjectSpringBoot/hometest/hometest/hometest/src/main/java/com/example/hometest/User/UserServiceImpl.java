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
            throw new ResourceRuntimeException("Exception", "Error", e.getMessage());
        }
    }

    @Override
    public User getUserByUserId(long UserId) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "UserId", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with param %s : '%s'", "User", "UserId", UserId));
            }

            return userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceRuntimeException(
                            String.format("%s does not exist with param %s : '%s'", "User", "UserId", UserId)));
        } catch (Exception e) {
            throw new ResourceRuntimeException("Exception", "Error", e.getMessage());
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            // Check error param
            if (user == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "User", "null"));
            } else if (String.valueOf(user.getUserId()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "UserId", "null"));
            } else if (String.valueOf(user.getFullName()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "FullName", "null"));
            } else if (String.valueOf(user.getPassword()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "Password", "null"));
            } else if (String.valueOf(user.getNotificationToken()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "NotificationToken", "null"));
            }

            // Check data exists
            if (userRepository.findById(user.getUserId()).isEmpty() == false) {
                throw new ResourceRuntimeException(
                        String.format("%s have exist with param %s : '%s'", "User", "UserId",
                                String.valueOf(user.getUserId())));
            }

            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceRuntimeException("Exception", "Error", e.getMessage());
        }
    }

    @Override
    public User updateUser(User user, long UserId) {
        try {
            // Check error param
            if (user == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "User", "null"));
            } else if (String.valueOf(UserId) == null || String.valueOf(user.getUserId()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "UserId", "null"));
            } else if (String.valueOf(user.getFullName()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "FullName", "null"));
            } else if (String.valueOf(user.getPassword()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "Password", "null"));
            } else if (String.valueOf(user.getNotificationToken()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "User", "NotificationToken", "null"));
            }

            // Check data exists
            if (UserId != user.getUserId()) {
                throw new ResourceRuntimeException(
                        String.format("%s is different data with param %s : '%s'", "User", "UserId",
                                String.valueOf(UserId) + " - " + String.valueOf(user.getUserId())));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException("User", "UserId", String.valueOf(UserId));
            }

            User existingUser = userRepository.findById(UserId).orElseThrow(
                    () -> new ResourceRuntimeException("User", "UserId", UserId));
            existingUser.setFullName(user.getFullName());
            existingUser.setPassword(user.getPassword());
            existingUser.setNotificationToken(user.getNotificationToken());
            userRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            throw new ResourceRuntimeException("Exception", "Error", e.getMessage());
        }
    }

    @Override
    public boolean deleteUser(long UserId) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with param %s : '%s'", "Account", "UserId", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException("User", "UserId", String.valueOf(UserId));
            }

            userRepository.deleteById(UserId);
            return true;
        } catch (Exception e) {
            throw new ResourceRuntimeException("Exception", "Error", e.getMessage());
        }
    }
}
