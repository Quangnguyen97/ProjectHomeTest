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
        List<User> listUser = userServiceImpl.getAllUsers()
                .stream()
                .map(post -> modelMapper.map(post, User.class))
                .collect(Collectors.toList());
        if (listUser.isEmpty()) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUserDto);
        } else {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    listUser);
            return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> getUserByUserId(@PathVariable(name = "userId") Long userId) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        try {
            User user = userServiceImpl.getUserByUserId(userId);
            if (user == null) {
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseUserDto);
            } else {
                List<User> ListUser = new ArrayList<User>();
                ListUser.add(modelMapper.map(user, User.class));
                responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(),
                        ListUser);
                return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
            }
        } catch (ResourceNotFoundException e) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase() + " - " + e,
                    null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseUserDto> saveUser(@RequestBody UserDto userDto) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        User user = userServiceImpl.saveUser(modelMapper.map(userDto, User.class));

        if (user == null) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserDto);
        } else {
            List<User> ListUser = new ArrayList<User>();
            ListUser.add(user);
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    ListUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable(name = "userId") Long userId,
            @RequestBody UserDto userDto) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        User user = userServiceImpl.updateUser(modelMapper.map(userDto, User.class), userId);

        if (user == null) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserDto);
        } else {
            List<User> ListUser = new ArrayList<User>();
            ListUser.add(user);
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase(),
                    ListUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserDto);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseUserDto> deleteUser(@PathVariable(name = "userId") Long userId) {
        ResponseUserDto responseUserDto = modelMapper.map(Response.class, ResponseUserDto.class);
        if (userServiceImpl.deleteUser(userId)) {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.getReasonPhrase() + " - User deleted successfully!",
                    null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserDto);
        } else {
            responseUserDto = ResponseUserDto(responseUserDto, HttpStatus.EXPECTATION_FAILED.value(),
                    HttpStatus.EXPECTATION_FAILED.getReasonPhrase() + " - User deleted failed!",
                    null);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseUserDto);
        }
    }

    private ResponseUserDto ResponseUserDto(ResponseUserDto responseUserDto, int errorCode, String errorDescription,
            List<User> listUser) {
        try {
            responseUserDto.setErrorCode(errorCode);
            responseUserDto.setErrorDescription(errorDescription);
            responseUserDto.setResponse(listUser);
            return responseUserDto;
        } catch (Exception e) {
            return null;
        }
    }
}
