package com.example.hometest.Account;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.hometest.Module.*;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts(long userId) {
        try {
            List<Account> ListAccount = accountRepository.findByUserId(userId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", userId);
                return null;
            } else {
                return ListAccount;
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public Account getAccountByNumber(long userId, int accountNumber) {
        try {
            List<Account> ListAccount = accountRepository.findByUserId(userId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", userId);
                return null;
            } else {
                if (String.valueOf(accountNumber) == null) {
                    return null;
                } else {
                    return accountRepository.findByAccountNumber(accountNumber);
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public Account saveAccount(long userId, Account account) {
        try {
            if (userId != account.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", userId));
                return null;
            }
            List<Account> ListAccount = accountRepository.findByUserId(userId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", userId);
                return null;
            } else {
                if (accountRepository.findByAccountNumber(account.getAccountNumber()) == null) {
                    return accountRepository.save(account);
                } else {
                    new ResourceNotFoundException(
                            String.format("%s is exists with %s : '%s'", "Account", "AccountNumber",
                                    account.getAccountNumber()));
                    return null;
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public Account updateAccount(long userId, Account account, int accountNumber) {
        try {
            if (userId != account.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", userId));
                return null;
            }
            if (accountNumber != account.getAccountNumber()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "AccountNumber", accountNumber));
                return null;
            }
            List<Account> ListAccount = accountRepository.findByUserId(userId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", userId);
                return null;
            } else {
                if (accountRepository.findByAccountNumber(account.getAccountNumber()) == null) {
                    new ResourceNotFoundException("Account", "AccountNumber", accountNumber);
                    return null;
                } else {
                    Account existingAccount = accountRepository.findByAccountNumber(accountNumber);
                    existingAccount.setAccountNumber(account.getAccountNumber());
                    existingAccount.setBalance(account.getBalance());
                    accountRepository.save(existingAccount);
                    return existingAccount;
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public void deleteAccount(long userId, int accountNumber) {
        try {
            List<Account> ListAccount = accountRepository.findByUserId(userId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", userId);
            } else {
                if (accountRepository.findByAccountNumber(accountNumber) == null) {
                    new ResourceNotFoundException("Account", "AccountNumber", accountNumber);
                } else {
                    accountRepository.deleteByAccountNumber(accountNumber);
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
        }
    }
}
