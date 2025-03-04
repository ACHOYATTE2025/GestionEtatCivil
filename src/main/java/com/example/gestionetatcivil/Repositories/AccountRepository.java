package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByEmail(String email);
    public Optional<Account> findByUsername(String username);
    public void delete(Account souscripteur);


}
