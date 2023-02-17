package com.example.hometest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hometest.Account.*;
import com.example.hometest.Module.*;
import com.example.hometest.Response.*;

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
    public ResponseEntity<ResponseAccountDto> getAllAccounts(@PathVariable(name = "userId") long userId) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            List<Account> listAccount = accountServiceImpl.getByUserId(userId)
                    .stream()
                    .map(post -> modelMapper.map(post, Account.class))
                    .collect(Collectors.toList());
            if (listAccount.isEmpty()) {
                throw new ResourceException("List account " + HttpStatus.NOT_FOUND.getReasonPhrase());
            } else {
                responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(), "", listAccount);
                return ResponseEntity.status(HttpStatus.OK).body(responseAccountDto);
            }
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    @GetMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseAccountDto> getAccountByNumber(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            Account account = accountServiceImpl.getByUserIdAndAccountNumber(userId, accountNumber);
            if (account == null) {
                throw new ResourceException("Account " + HttpStatus.NOT_FOUND.getReasonPhrase());
            } else {
                List<Account> listAccount = new ArrayList<Account>();
                listAccount.add(account);
                responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(), "", listAccount);
                return ResponseEntity.status(HttpStatus.OK).body(responseAccountDto);
            }
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    @PostMapping("/user/{userId}/account")
    public ResponseEntity<ResponseAccountDto> saveAccount(@PathVariable(name = "userId") long userId,
            @RequestBody @Valid AccountDto accountDto) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            Account account = accountServiceImpl.saveAccount(userId, modelMapper.map(accountDto, Account.class));
            if (account == null) {
                throw new ResourceException("Account created failed");
            } else {
                List<Account> listAccount = new ArrayList<Account>();
                listAccount.add(account);
                responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.getReasonPhrase(), "Account created successfully", listAccount);
                return ResponseEntity.status(HttpStatus.OK).body(responseAccountDto);
            }
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    @PutMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseAccountDto> updateAccount(@PathVariable(name = "userId") long userId,
            @RequestBody @Valid AccountDto accountDto, @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            Account account = accountServiceImpl.updateAccount(userId, modelMapper.map(accountDto, Account.class),
                    accountNumber);
            if (account == null) {
                throw new ResourceException("Account updated failed");
            } else {
                List<Account> listAccount = new ArrayList<Account>();
                listAccount.add(account);
                responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "Account updated successfully", listAccount);
                return ResponseEntity.status(HttpStatus.OK).body(responseAccountDto);
            }
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    @DeleteMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseAccountDto> deleteAccount(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            if (accountServiceImpl.deleteAccount(userId, accountNumber)) {
                responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "Account deleted successfully", null);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseAccountDto);
            } else {
                throw new ResourceException("Account deleted failed");
            }
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ResponseAccountDto> HandleHttpMessageException(
            HttpMessageNotReadableException exception) {
        ResponseAccountDto responseAccountDto = modelMapper.map(Response.class, ResponseAccountDto.class);
        try {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        } catch (Exception e) {
            responseAccountDto = ResponseAccountDto(responseAccountDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseAccountDto);
        }
    }

    private ResponseAccountDto ResponseAccountDto(ResponseAccountDto responseAccountDto, int status, String description,
            String message, List<Account> listAccount) {
        try {
            responseAccountDto.setStatus(status);
            responseAccountDto.setDescription(description);
            responseAccountDto.setMessage(message);
            responseAccountDto.setResponse(listAccount);
            return responseAccountDto;
        } catch (Exception e) {
            throw new ResourceException();
        }
    }
}
