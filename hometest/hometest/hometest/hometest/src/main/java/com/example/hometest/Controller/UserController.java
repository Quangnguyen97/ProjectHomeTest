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

import com.example.hometest.User.*;
import com.example.hometest.Module.*;
import com.example.hometest.Response.*;

@RestController
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        super();
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> getAllUsers() {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            List<User> listUser = userServiceImpl.getAllUsers()
                    .stream()
                    .map(post -> modelMapper.map(post, User.class))
                    .collect(Collectors.toList());
            if (listUser.isEmpty()) {
                throw new ResourceException("List user " + HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            List<Object> listObject = new ArrayList<Object>();
            for (User user : listUser) {
                listObject.add(user);
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> getUserByUserId(@PathVariable(name = "userId") Long userId) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            User user = userServiceImpl.getUserByUserId(userId);
            if (user == null) {
                throw new ResourceException("User " + HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(user);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), "", listObject);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> saveUser(@RequestBody @Valid UserDto userDto) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            User user = userServiceImpl.saveUser(modelMapper.map(userDto, User.class));
            if (user == null) {
                throw new ResourceException("User created failed");
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(user);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(), "User created successfully", listObject);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable(name = "userId") Long userId,
            @RequestBody @Valid UserDto userDto) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            User user = userServiceImpl.updateUser(modelMapper.map(userDto, User.class), userId);
            if (user == null) {
                throw new ResourceException("User updated failed");
            }
            List<Object> listObject = new ArrayList<Object>();
            listObject.add(user);
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(), "User updated successfully", listObject);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseDto);
        } catch (Exception e) {
            ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable(name = "userId") Long userId) {
        ResponseDto ResponseDto = modelMapper.map(Response.class, ResponseDto.class);
        try {
            if (userServiceImpl.deleteUser(userId)) {
                ResponseDto = ResourceResponse.ResponseDto(ResponseDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "User deleted successfully", null);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseDto);
            } else {
                throw new ResourceException("User deleted failed");
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
