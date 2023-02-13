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
    public User getUserByUserId(long Userid) {
        try {
            return userRepository.findById(Userid).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", Userid));
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User saveUser(User User) {
        try {
            return userRepository.save(User);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public User updateUser(User User, long Userid) {
        try {
            User existingUser = userRepository.findById(Userid).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", Userid));

            existingUser.setFullName(User.getFullName());
            existingUser.setPassword(User.getPassword());
            existingUser.setNotificationToken(User.getNotificationToken());
            userRepository.save(existingUser);
            return existingUser;
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public void deleteUser(long Userid) {
        try {
            userRepository.findById(Userid).orElseThrow(
                    () -> new ResourceNotFoundException("User", "Userid", Userid));
            userRepository.deleteById(Userid);

        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
        }
    }
}
