package com.example.hometest.MapStruct;

import org.mapstruct.Mapper;

import com.example.hometest.Account.*;
import com.example.hometest.User.*;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AccountDto accountDto(Account account);

    UserDto userDto(User user);
}
