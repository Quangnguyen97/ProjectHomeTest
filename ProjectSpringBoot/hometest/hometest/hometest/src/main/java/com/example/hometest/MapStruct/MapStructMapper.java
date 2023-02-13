package com.example.hometest.MapStruct;

import org.mapstruct.Mapper;
import com.example.hometest.Account.Account;
import com.example.hometest.Account.AccountDto;
import com.example.hometest.User.User;
import com.example.hometest.User.UserDto;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    AccountDto accountDto(Account account);

    UserDto userDto(User user);
}
