package com.example.hometest.Account;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.hometest.Module.*;
import com.example.hometest.User.*;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        super();
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;

    }

    @Override
    public List<Account> getAllAccounts(long UserId) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return null;
            }

            // Check data exists
            if (accountRepository.findByUserId(UserId).isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", String.valueOf(UserId));
                return null;
            }

            return accountRepository.findByUserId(UserId);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Account getAccountByNumber(long UserId, long AccountNumber) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return null;
            } else if (String.valueOf(AccountNumber) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "AccountNumber", "null"));
                return null;
            }

            // Check data exists
            if (accountRepository.findByUserId(UserId).isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", String.valueOf(UserId));
                return null;
            } else if (accountRepository.findByUserId(AccountNumber) == null) {
                new ResourceNotFoundException("Account", "AccountNumber", String.valueOf(AccountNumber));
                return null;
            }

            return accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Account saveAccount(long UserId, Account account) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return null;
            } else if (String.valueOf(account.getUserId()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return null;
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is different data with %s : '%s'", "Account", "UserId",
                                String.valueOf(UserId) + " - " + String.valueOf(account.getUserId())));
                return null;
            } else if (userRepository.findById(UserId) == null) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return null;
            } else if (accountRepository.findById(account.getAccountNumber()) != null) {
                new ResourceNotFoundException(
                        String.format("%s is exists with %s : '%s'", "Account", "AccountNumber",
                                String.valueOf(account.getAccountNumber())));
                return null;
            }

            return accountRepository.save(account);
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Account updateAccount(long UserId, Account account, long AccountNumber) {
        try {
            // Check error param
            if (account == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "Account", "null"));
                return null;
            } else if (String.valueOf(UserId) == null || String.valueOf(account.getUserId()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return null;
            } else if (String.valueOf(AccountNumber) == null || String.valueOf(account.getAccountNumber()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "AccountNumber", "null"));
                return null;
            } else if (String.valueOf(account.getBalance()) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "Balance", "null"));
                return null;
            } else if (AccountNumber != account.getAccountNumber()) {
                new ResourceNotFoundException(
                        String.format("%s is different data with %s : '%s'", "Account", "AccountNumber",
                                String.valueOf(AccountNumber) + " - " + String.valueOf(account.getAccountNumber())));
                return null;
            }

            // Check data exists
            if (userRepository.findById(UserId) == null) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return null;
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                new ResourceNotFoundException("Account", "UserId - AccountNumber",
                        String.valueOf(UserId) + " - " + String.valueOf(AccountNumber));
                return null;
            }

            Account existingAccount = accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
            existingAccount.setUserId(account.getUserId());
            existingAccount.setBalance(account.getBalance());
            accountRepository.save(existingAccount);
            return existingAccount;
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", String.valueOf(e));
            return null;
        }
    }

    @Override
    public boolean deleteAccount(long UserId, long AccountNumber) {
        try {
            // Check error param
            if (String.valueOf(UserId) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", "null"));
                return false;
            } else if (String.valueOf(AccountNumber) == null) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "AccountNumber", "null"));
                return false;
            }

            // Check data exists
            if (userRepository.findById(UserId) == null) {
                new ResourceNotFoundException("User", "UserId", String.valueOf(UserId));
                return false;
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                new ResourceNotFoundException("Account", "UserId - AccountNumber",
                        String.valueOf(UserId) + " - " + String.valueOf(AccountNumber));
                return false;
            }

            accountRepository.deleteById(AccountNumber);
            return true;
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", String.valueOf(e));
            return false;
        }
    }
}
