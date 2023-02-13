package com.example.hometest.MapStruct;

import org.springframework.stereotype.Component;
import jakarta.annotation.Generated;
import com.example.hometest.Account.Account;
import com.example.hometest.Account.AccountDto;
import com.example.hometest.User.User;
import com.example.hometest.User.UserDto;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2021-03-11T19:21:44+0100", comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)")
@Component
public class MapStructMapperImpl implements MapStructMapper {
    @Override
    public AccountDto accountDto(Account account) {
        if (account == null) {
            return null;
        }
        AccountDto accountDto = new AccountDto();
        accountDto.setUserId(account.getUserId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());
        return accountDto;
    }

    @Override
    public UserDto userDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFullName(user.getFullName());
        userDto.setPassword(user.getPassword());
        userDto.setNotificationToken(user.getNotificationToken());
        return userDto;
    }
}
