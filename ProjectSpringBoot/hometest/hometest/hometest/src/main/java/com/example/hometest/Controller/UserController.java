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

import com.example.hometest.User.*;

@RestController
public class UserController {
    @Autowired
    private ModelMapper modelMapper;

    private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        super();
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/user")
    public List<User> getAllUsers() {
        List<User> ListUser = userServiceImpl.getAllUsers()
                .stream()
                .map(post -> modelMapper.map(post, User.class))
                .collect(Collectors.toList());
        if (ListUser.isEmpty()) {
            return null;
        } else {
            return ListUser;
        }
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable(name = "userId") Long userId) {
        User user = userServiceImpl.getUserByUserId(userId);

        if (user != null) {
            // convert entity to DTO
            UserDto userResponse = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok().body(userResponse);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        // convert DTO to entity
        User userRequest = modelMapper.map(userDto, User.class);
        User user = userServiceImpl.saveUser(userRequest);

        if (user != null) {
            // convert entity to DTO
            UserDto userResponse = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<UserDto>(userResponse, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "userId") Long userId,
            @RequestBody UserDto userDto) {
        // convert DTO to Entity
        User userRequest = modelMapper.map(userDto, User.class);
        User user = userServiceImpl.updateUser(userRequest, userId);

        if (user != null) {
            // entity to DTO
            UserDto userResponse = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok().body(userResponse);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "userId") Long userId) {
        if (userServiceImpl.deleteUser(userId)) {
            return new ResponseEntity<String>("User deleted successfully!.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User deleted failed!.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
