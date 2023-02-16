package com.example.hometest.Controller;

import java.util.ArrayList;
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

import com.example.hometest.User.*;

import jakarta.validation.Valid;

import com.example.hometest.Module.*;
import com.example.hometest.Response.*;

@RestController
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        super();
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseUserDto> getAllResponseUsers() {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            List<User> listUser = userServiceImpl.getAllUsers()
                    .stream()
                    .map(post -> modelMapper.map(post, User.class))
                    .collect(Collectors.toList());
            if (listUser.isEmpty()) {
                throw new ResourceRuntimeException();
            } else {
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(), "", listUser);
                return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
            }
        } catch (Exception e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> getUserByUserId(@PathVariable(name = "userId") Long userId) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            User user = userServiceImpl.getUserByUserId(userId);
            if (user == null) {
                throw new ResourceRuntimeException();
            } else {
                List<User> listUser = new ArrayList<User>();
                listUser.add(user);
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(), "", listUser);
                return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
            }
        } catch (Exception e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseUserDto> saveUser(@RequestBody UserDto userDto) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            User user = userServiceImpl.saveUser(modelMapper.map(userDto, User.class));
            if (user == null) {
                throw new ResourceRuntimeException();
            } else {
                List<User> listUser = new ArrayList<User>();
                listUser.add(user);
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.getReasonPhrase(), "User created successfully", listUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
            }
        } catch (Exception e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable(name = "userId") Long userId,
            @RequestBody @Valid UserDto userDto) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            User user = userServiceImpl.updateUser(modelMapper.map(userDto, User.class), userId);
            if (user == null) {
                throw new ResourceRuntimeException();
            } else {
                List<User> listUser = new ArrayList<User>();
                listUser.add(user);
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "User updated successfully", listUser);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserDto);
            }
        } catch (Exception e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> deleteUser(@PathVariable(name = "userId") Long userId) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            if (userServiceImpl.deleteUser(userId)) {
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.ACCEPTED.value(),
                        HttpStatus.ACCEPTED.getReasonPhrase(), "User deleted successfully", null);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserDto);
            } else {
                throw new ResourceRuntimeException();
            }
        } catch (Exception e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    private ResponseUserDto ResponseUserDto(ResponseUserDto responseUserDto, int status, String description,
            String message, List<User> listUser) {
        try {
            responseUserDto.setStatus(status);
            responseUserDto.setDescription(description);
            responseUserDto.setMessage(message);
            responseUserDto.setResponse(listUser);
            return responseUserDto;
        } catch (Exception e) {
            throw new ResourceRuntimeException();
        }
    }
}
