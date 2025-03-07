package com.example.gestionetatcivil.MapperDto;

import com.example.gestionetatcivil.Dto.AccountAdminDto;
import com.example.gestionetatcivil.Entities.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountMapperDto implements Function<Account, AccountAdminDto> {

    @Override
    public AccountAdminDto apply(Account subscriber) {
        return new AccountAdminDto(subscriber.getId(), subscriber.getEmail(),subscriber.getPassword());
    }

}
