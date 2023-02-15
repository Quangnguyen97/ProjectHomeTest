package com.example.hometest.Account;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.hometest.User.*;
import com.example.hometest.Module.*;

@Service
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private ResourceValidString resourceValidString;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        super();
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;

    }

    @Override
    public List<Account> getAllAccounts(long UserId) {
        try {
            // Check error field
            if (String.valueOf(UserId) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "UserId", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "UserId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findByUserId(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "ListAccount", "UserId",
                                String.valueOf(UserId)));
            }

            return accountRepository.findByUserId(UserId);
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public Account getAccountByNumber(long UserId, long AccountNumber) {
        try {
            // Check error field
            if (resourceValidString.ValidStringIsOk(String.valueOf(UserId))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "UserId", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(AccountNumber))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "AccountNumber", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "UserId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findByUserId(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "ListAccount", "UserId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "Account", "AccountNumber",
                                String.valueOf(AccountNumber)));
            } else if (String.valueOf(
                    accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber).getAccountNumber()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s' and %s='%s'", "Account", "UserId",
                                String.valueOf(UserId), "AccountNumber", String.valueOf(AccountNumber)));
            }

            return accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public Account saveAccount(long UserId, Account account) {
        try {
            // Check error field
            if (resourceValidString.ValidStringIsOk(String.valueOf(UserId))
                    || resourceValidString.ValidStringIsOk(String.valueOf(account.getUserId()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "UserId", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(account.getAccountNumber()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "AccountNumber", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(account.getBalance()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "Balance", "null"));
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                throw new ResourceRuntimeException(
                        String.format("%s is different data with field %s:'%s', '%s'", "Account", "UserId",
                                String.valueOf(UserId), String.valueOf(account.getUserId())));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "UserId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findById(account.getAccountNumber()).isEmpty() == false) {
                throw new ResourceRuntimeException(
                        String.format("%s have exist with field %s:'%s'", "Account", "AccountNumber",
                                String.valueOf(account.getAccountNumber())));
            } else if (String.valueOf(
                    accountRepository.findByUserIdAndAccountNumber(UserId, account.getAccountNumber())
                            .getAccountNumber()) != null) {
                throw new ResourceRuntimeException(
                        String.format("%s have exist with field %s='%s' and %s='%s'", "Account", "UserId",
                                String.valueOf(UserId), "AccountNumber", String.valueOf(account.getAccountNumber())));
            }

            return accountRepository.save(account);
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public Account updateAccount(long UserId, Account account, long AccountNumber) {
        try {
            // Check error field
            if (account == null) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "Account", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(UserId))
                    || resourceValidString.ValidStringIsOk(String.valueOf(account.getUserId()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "UserId", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(AccountNumber))
                    || resourceValidString.ValidStringIsOk(String.valueOf(account.getAccountNumber()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "AccountNumber", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(account.getBalance()))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "Balance", "null"));
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                throw new ResourceRuntimeException(
                        String.format("%s is different data with field %s:'%s', '%s'", "Account", "UserId",
                                String.valueOf(UserId), String.valueOf(account.getUserId())));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "UserId",
                                String.valueOf(UserId)));
            } else if (AccountNumber != account.getAccountNumber()) {
                throw new ResourceRuntimeException(
                        String.format("%s is different data with field %s:'%s', '%s'", "Account", "AccountNumber",
                                String.valueOf(AccountNumber), String.valueOf(account.getAccountNumber())));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "Account", "AccountNumber",
                                String.valueOf(AccountNumber)));
            } else if (String.valueOf(
                    accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber).getAccountNumber()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s' and %s='%s'", "Account", "UserId",
                                String.valueOf(UserId), "AccountNumber", String.valueOf(AccountNumber)));
            }

            Account existingAccount = accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
            existingAccount.setUserId(account.getUserId());
            existingAccount.setBalance(account.getBalance());
            accountRepository.save(existingAccount);
            return existingAccount;
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteAccount(long UserId, long AccountNumber) {
        try {
            // Check error field
            if (resourceValidString.ValidStringIsOk(String.valueOf(UserId))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "UserId", "null"));
            } else if (resourceValidString.ValidStringIsOk(String.valueOf(AccountNumber))) {
                throw new ResourceRuntimeException(
                        String.format("%s have error with field %s='%s'", "Account", "AccountNumber", "null"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "User", "UserId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s'", "Account", "AccountNumber",
                                String.valueOf(AccountNumber)));
            } else if (String.valueOf(
                    accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber).getAccountNumber()) == null) {
                throw new ResourceRuntimeException(
                        String.format("%s does not exist with field %s='%s' and %s='%s'", "Account", "UserId",
                                String.valueOf(UserId), "AccountNumber", String.valueOf(AccountNumber)));
            }

            accountRepository.deleteById(AccountNumber);
            return true;
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
