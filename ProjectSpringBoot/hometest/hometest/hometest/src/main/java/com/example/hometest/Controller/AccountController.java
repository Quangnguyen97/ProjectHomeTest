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

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    public AccountController(AccountServiceImpl accountServiceImpl) {
        super();
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping("/user/{userId}/account")
    public ResponseEntity<ResponseDto> getAllAccounts(@PathVariable(name = "userId") long userId) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            List<Account> listAccount = accountServiceImpl.getByUserId(userId)
                    .stream()
                    .map(post -> modelMapper.map(post, Account.class))
                    .collect(Collectors.toList());
            if (listAccount.isEmpty()) {
                throw new ResourceException("List account " + HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            List<Object> listObject = new ArrayList<Object>();
            for (Account account : listAccount) {
                listObject.add(account);
            }
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), "", listObject);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @GetMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseDto> getAccountByNumber(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            Account account = accountServiceImpl.getByUserIdAndAccountNumber(userId, accountNumber);
            if (account == null) {
                throw new ResourceException("Account " + HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(account);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), "", listObject);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @PostMapping("/user/{userId}/account")
    public ResponseEntity<ResponseDto> saveAccount(@PathVariable(name = "userId") long userId,
            @RequestBody @Valid AccountDto accountDto) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            Account account = accountServiceImpl.saveAccount(userId, modelMapper.map(accountDto, Account.class));
            if (account == null) {
                throw new ResourceException("Account created failed");
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(account);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(), "Account created successfully", listObject);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @PutMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseDto> updateAccount(@PathVariable(name = "userId") long userId,
            @RequestBody @Valid AccountDto accountDto, @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            Account account = accountServiceImpl.updateAccount(userId, modelMapper.map(accountDto, Account.class),
                    accountNumber);
            if (account == null) {
                throw new ResourceException("Account updated failed");
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(account);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(), "Account updated successfully", listObject);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @DeleteMapping("/user/{userId}/account/{accountNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable(name = "userId") long userId,
            @PathVariable(name = "accountNumber") int accountNumber) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            if (accountServiceImpl.deleteAccount(userId, accountNumber)) {
                ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "Account deleted successfully", null);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseDto);
            } else {
                throw new ResourceException("Account deleted failed");
            }
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ResponseDto> HandleHttpMessageException(
            HttpMessageNotReadableException exception) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }
}
