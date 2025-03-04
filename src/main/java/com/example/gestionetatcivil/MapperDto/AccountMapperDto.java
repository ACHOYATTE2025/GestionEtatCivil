package com.example.birthadvance.MapperDto;

import com.example.birthadvance.Dto.AccountAdminDto;
import com.example.birthadvance.Entities.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountMapperDto implements Function<Account, AccountAdminDto> {

    @Override
    public AccountAdminDto apply(Account subscriber) {
        return new AccountAdminDto(subscriber.getId(), subscriber.getEmail(),subscriber.getPassword());
    }

}
