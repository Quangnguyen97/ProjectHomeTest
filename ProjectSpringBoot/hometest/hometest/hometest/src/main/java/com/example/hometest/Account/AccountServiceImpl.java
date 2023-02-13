package com.example.hometest.Account;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.hometest.Module.*;

@Service("AccountServiceImpl")
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts(Iterable<Long> UserId) {
        try {
            List<Account> ListAccount = accountRepository.findAllById(UserId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", UserId);
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
    public Account getAccountByNumber(Iterable<Long> UserId, int AccountNumber) {
        try {
            List<Account> ListAccount = accountRepository.findAllById(UserId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", UserId);
                return null;
            } else {
                if (String.valueOf(AccountNumber) == null) {
                    return null;
                } else {
                    return accountRepository.findByAccountNumber(AccountNumber);
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
            return null;
        }
    }

    @Override
    public Account saveAccount(Iterable<Long> UserId, Account account) {
        try {
            if (UserId != account.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", UserId));
                return null;
            }
            List<Account> ListAccount = accountRepository.findAllById(UserId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", UserId);
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
    public Account updateAccount(Iterable<Long> UserId, Account account, int AccountNumber) {
        try {
            if (UserId != account.getUserId()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "UserId", UserId));
                return null;
            }
            if (AccountNumber != account.getAccountNumber()) {
                new ResourceNotFoundException(
                        String.format("%s is error param with %s : '%s'", "Account", "AccountNumber", AccountNumber));
                return null;
            }
            List<Account> ListAccount = accountRepository.findAllById(UserId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", UserId);
                return null;
            } else {
                if (accountRepository.findByAccountNumber(account.getAccountNumber()) == null) {
                    new ResourceNotFoundException("Account", "AccountNumber", AccountNumber);
                    return null;
                } else {
                    Account existingAccount = accountRepository.findByAccountNumber(AccountNumber);
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
    public void deleteAccount(Iterable<Long> UserId, int AccountNumber) {
        try {
            List<Account> ListAccount = accountRepository.findAllById(UserId);
            if (ListAccount.isEmpty()) {
                new ResourceNotFoundException("ListAccount", "UserId", UserId);
            } else {
                if (accountRepository.findByAccountNumber(AccountNumber) == null) {
                    new ResourceNotFoundException("Account", "AccountNumber", AccountNumber);
                } else {
                    accountRepository.deleteByAccountNumber(AccountNumber);
                }
            }
        } catch (Exception e) {
            new ResourceErrorException("Exception", "Error", e);
        }
    }
}
