package com.example.birthadvance.Service;

import com.example.birthadvance.Entities.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContextSouscripteur {

    public Optional<Account> getSouscripteur() {
        Account sub = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(sub != null) {return Optional.of(sub);}else
        {return null;}
        }
}
