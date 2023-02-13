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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hometest.Account.*;

@RestController
@RequestMapping("/User/{userId}/Account")
public class AccountController {
    @Autowired
    private ModelMapper modelMapper;

    private AccountServiceImpl accountServiceImpl;

    public AccountController(AccountServiceImpl accountServiceImpl) {
        super();
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping
    public List<Account> getAllAccounts(@PathVariable(name = "userId") Iterable<Long> userId) {
        return accountServiceImpl.getAllAccounts(userId)
                .stream()
                .map(post -> modelMapper.map(post, Account.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable(name = "userId") Iterable<Long> userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        Account account = accountServiceImpl.getAccountByNumber(userId, accountNumber);
        // convert entity to DTO
        AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
        return ResponseEntity.ok().body(accountResponse);
    }

    @PostMapping
    public ResponseEntity<AccountDto> saveAccount(@PathVariable(name = "userId") Iterable<Long> userId,
            @RequestBody AccountDto accountDto) {
        // convert DTO to entity
        Account accountRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountServiceImpl.saveAccount(userId, accountRequest);
        // convert entity to DTO
        AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
        return new ResponseEntity<AccountDto>(accountResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable(name = "userId") Iterable<Long> userId,
            @RequestBody AccountDto accountDto, @PathVariable(name = "accountNumber") int accountNumber) {
        // convert DTO to Entity
        Account accountRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountServiceImpl.updateAccount(userId, accountRequest, accountNumber);
        // entity to DTO
        AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
        return ResponseEntity.ok().body(accountResponse);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "userId") Iterable<Long> userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        accountServiceImpl.deleteAccount(userId, accountNumber);
        return new ResponseEntity<String>("User deleted successfully!.", HttpStatus.OK);
    }
}
