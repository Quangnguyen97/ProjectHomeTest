package com.example.hometest.User;

import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

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
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    public User getUserByUserId(long UserId) {
        try {
            // Check error field
            if (ResourceValid.StrIsError(String.valueOf(UserId)) || UserId < 1) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            }

            return userRepository.findById(UserId).orElseThrow(() -> new ResourceException(
                    ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId")));
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        try {
            // Check error field
            if (user == null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.REQUEST, "User"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getUserId())) || user.getUserId() < 1) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getFullName()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "fullName"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getPassWord()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "password"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getNotificationToken()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "notificationToken"));
            }

            // Check data exists
            if (userRepository.findById(user.getUserId()).isEmpty() == false) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.EXISTED, "userId"));
            }

            User returnUser = userRepository.save(user);
            userRepository.flush();
            return returnUser;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User updateUser(User user, long UserId) {
        try {
            // Check error field
            if (user == null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.REQUEST, "User"));
            } else if (ResourceValid.StrIsError(String.valueOf(UserId))
                    || ResourceValid.StrIsError(String.valueOf(user.getUserId()))
                    || UserId < 1 || user.getUserId() < 1) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getFullName()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "fullName"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getPassWord()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "password"));
            } else if (ResourceValid.StrIsError(String.valueOf(user.getNotificationToken()))) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "notificationToken"));
            }

            // Check data exists
            if (UserId != user.getUserId()) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.DIFFERENT, "userId"));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            }

            User existingUser = userRepository.findById(UserId).orElseThrow(() -> new ResourceException(
                    ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId")));
            existingUser.setFullName(user.getFullName());
            existingUser.setPassWord(user.getPassWord());
            existingUser.setNotificationToken(user.getNotificationToken());
            userRepository.save(existingUser);
            userRepository.flush();
            return existingUser;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(long UserId) {
        try {
            // Check error field
            if (ResourceValid.StrIsError(String.valueOf(UserId)) || UserId < 1) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            }

            // Check account exists
            if (accountRepository.findByUserId(UserId).isEmpty() == false) {
                try {
                    accountRepository.deleteByUserId(UserId);
                } catch (Exception e) {
                    throw new ResourceException(e.getMessage());
                }
            }

            userRepository.deleteById(UserId);
            userRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
