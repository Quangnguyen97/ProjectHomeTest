package com.example.hometest.Account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.example.hometest.User.*;
import com.example.hometest.Module.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        super();
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getByUserId(long UserId) {
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
    public Account getByUserIdAndAccountNumber(long UserId, long AccountNumber) {
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
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.REQUEST, "Account"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, UserId)
                    || ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, account.getUserId())
                    || UserId < 1 || account.getUserId() < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, AccountNumber)
                    || ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, account.getAccountNumber())
                    || AccountNumber < 1 || account.getAccountNumber() < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "accountNumber"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.DOUBLE, account.getAccountBalance())) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "balance"));
            }

            // Check data exists
            if (UserId != account.getUserId()) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.DIFFERENT, "userId"));
            } else if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            } else if (AccountNumber != account.getAccountNumber()) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.DIFFERENT, "accountNumber"));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "accountNumber"));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId, accountNumber"));
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
            if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, UserId) || UserId < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "userId"));
            } else if (ResourceValid.ObjectIsError(ResourceValid.typeOBJECT.LONG, AccountNumber) || AccountNumber < 1) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.FIELD, "accountNumber"));
            }

            // Check data exists
            if (userRepository.findById(UserId).isEmpty() == true) {
                throw new ResourceException(ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId"));
            } else if (accountRepository.findById(AccountNumber).isEmpty() == true) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "accountNumber"));
            } else if (accountRepository.findByUserIdAndAccountNumber(UserId, AccountNumber) == null) {
                throw new ResourceException(
                        ResourceValid.StringError(ResourceValid.typeERROR.NOTEXISTED, "userId, accountNumber"));
            }

            accountRepository.deleteById(AccountNumber);
            accountRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
