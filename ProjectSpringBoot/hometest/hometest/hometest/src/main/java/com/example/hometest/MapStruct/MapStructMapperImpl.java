package com.example.hometest.MapStruct;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import jakarta.annotation.Generated;

import com.example.hometest.Account.*;
import com.example.hometest.User.*;

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
        userDto.setPassword(user.getPassWord());
        userDto.setNotificationToken(user.getNotificationToken());
        return userDto;
    }

    protected Set<Account> authorSetToAuthorDtoSet(Set<Account> setAccount) {
        if (setAccount == null) {
            return null;
        }
        Set<Account> newSetAccount = new HashSet<Account>(
                Math.max((int) (setAccount.size() / .75f) + 1, 16));
        for (Account account : setAccount) {
            newSetAccount.add(account);
        }
        return newSetAccount;
    }
}
