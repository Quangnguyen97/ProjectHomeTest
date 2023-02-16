package com.example.hometest.Account;

import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.example.hometest.User.*;
import com.example.hometest.Module.*;

@Service
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        super();
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts(long UserId) {
        try {
            // Check error field
            if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, UserId) || UserId < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true
                    || accountRepository.findByUserId(UserId).isEmpty() == true) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            }

            return accountRepository.findByUserId(UserId);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    public Account getAccountByNumber(long UserId, long AccountNumber) {
        try {
            // Check error field
            if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, UserId) || UserId < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, AccountNumber) || AccountNumber < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "accountNumber"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true
                    || accountRepository.findByUserId(UserId).isEmpty() == true) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "accountNumber"));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId, accountNumber"));
            }

            return accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
        } catch (

        Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Account saveAccount(long UserId, Account account) {
        try {
            // Check error field
            if (account == null) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.REQUEST, "Account"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, UserId)
                    || ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, account.getUserId())
                    || UserId < 1 || account.getUserId() < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, account.getAccountNumber())
                    || account.getAccountNumber() < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "accountNumber"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.DOUBLE, account.getAccountBalance())) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "balance"));
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.DIFFERENT, "userId"));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            } else if (accountRepository.findById(account.getAccountNumber()).isEmpty() == false) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.EXISTED, "accountNumber"));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, account.getAccountNumber()) != null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.EXISTED, "userId, accountNumber"));
            }

            Account returnAccount = accountRepository.save(account);
            accountRepository.flush();
            return returnAccount;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Account updateAccount(long UserId, Account account, long AccountNumber) {
        try {
            // Check error field
            if (account == null) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "Account", "null"));
            } else if (ResourceValid.StrIsError(String.valueOf(UserId))
                    || ResourceValid.StrIsError(String.valueOf(account.getUserId()))) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "userId", "null"));
            } else if (UserId < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "userId", String.valueOf(UserId)));
            } else if (account.getUserId() < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "userId",
                                String.valueOf(account.getUserId())));
            } else if (ResourceValid.StrIsError(String.valueOf(AccountNumber))
                    || ResourceValid.StrIsError(String.valueOf(account.getAccountNumber()))) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "accountNumber", "null"));
            } else if (AccountNumber < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "accountNumber",
                                String.valueOf(AccountNumber)));
            } else if (account.getAccountNumber() < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "accountNumber",
                                String.valueOf(account.getAccountNumber())));
            } else if (ResourceValid.StrIsError(String.valueOf(account.getAccountBalance()))) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "balance", "null"));
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                throw new ResourceException(
                        String.format("%s is different request body with field %s:'%s', '%s'", "Account", "userId",
                                String.valueOf(UserId), String.valueOf(account.getUserId())));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s'", "User", "userId",
                                String.valueOf(UserId)));
            } else if (AccountNumber != account.getAccountNumber()) {
                throw new ResourceException(
                        String.format("%s is different request body with field %s:'%s', '%s'", "Account",
                                "accountNumber",
                                String.valueOf(AccountNumber), String.valueOf(account.getAccountNumber())));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s'", "Account", "accountNumber",
                                String.valueOf(AccountNumber)));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s' and %s='%s'", "Account", "userId",
                                String.valueOf(UserId), "accountNumber", String.valueOf(AccountNumber)));
            }

            Account existingAccount = accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber);
            existingAccount.setUserId(account.getUserId());
            existingAccount.setAccountBalance(account.getAccountBalance());
            accountRepository.save(existingAccount);
            accountRepository.flush();
            return existingAccount;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteAccount(long UserId, long AccountNumber) {
        try {
            // Check error field
            if (ResourceValid.StrIsError(String.valueOf(UserId))) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "userId", "null"));
            } else if (UserId < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "userId", String.valueOf(UserId)));
            } else if (ResourceValid.StrIsError(String.valueOf(AccountNumber))) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "accountNumber", "null"));
            } else if (AccountNumber < 1) {
                throw new ResourceException(
                        String.format("%s have error with field %s='%s'", "Account", "accountNumber",
                                String.valueOf(AccountNumber)));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s'", "User", "userId",
                                String.valueOf(UserId)));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s'", "Account", "accountNumber",
                                String.valueOf(AccountNumber)));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                throw new ResourceException(
                        String.format("%s does not exist with field %s='%s' and %s='%s'", "Account", "userId",
                                String.valueOf(UserId), "accountNumber", String.valueOf(AccountNumber)));
            }

            accountRepository.deleteById(AccountNumber);
            accountRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
