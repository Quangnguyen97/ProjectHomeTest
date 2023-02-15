package com.example.hometest.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hometest.Account.*;

@RestController
public class AccountController {
    @Autowired
    private ModelMapper modelMapper;
    private AccountServiceImpl accountServiceImpl;

    public AccountController(AccountServiceImpl accountServiceImpl) {
        super();
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping("/user/{userId}/account")
    public List<Account> getAllAccounts(@PathVariable(name = "userId") long userId) {
        List<Account> ListAccount = accountServiceImpl.getAllAccounts(userId)
                .stream()
                .map(post -> modelMapper.map(post, Account.class))
                .collect(Collectors.toList());
        if (ListAccount.isEmpty()) {
            return null;
        } else {
            return ListAccount;
        }
    }

    @GetMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        Account account = accountServiceImpl.getAccountByNumber(userId, accountNumber);

        if (account != null) {
            // convert entity to DTO
            AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
            return ResponseEntity.ok().body(accountResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/{userId}/account")
    public ResponseEntity<AccountDto> saveAccount(@PathVariable(name = "userId") long userId,
            @RequestBody AccountDto accountDto) {
        // convert DTO to entity
        Account accountRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountServiceImpl.saveAccount(userId, accountRequest);

        if (account != null) {
            // convert entity to DTO
            AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
            return new ResponseEntity<AccountDto>(accountResponse, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable(name = "userId") long userId,
            @RequestBody AccountDto accountDto, @PathVariable(name = "accountNumber") int accountNumber) {
        // convert DTO to Entity
        Account accountRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountServiceImpl.updateAccount(userId, accountRequest, accountNumber);

        if (account != null) {
            // entity to DTO
            AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
            return ResponseEntity.ok().body(accountResponse);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        if (accountServiceImpl.deleteAccount(userId, accountNumber)) {
            return new ResponseEntity<String>("Account deleted successfully!.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Account deleted failed!.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
