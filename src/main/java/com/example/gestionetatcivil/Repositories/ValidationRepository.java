package com.example.gestionetatcivil.Repositories;


import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Validation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {
     Optional<Validation> findByCode(String code);
     Optional<Validation> deleteByCode(String code);
     Optional<Validation> findById(Long id);
     Optional<Validation> findBySubscriber(Account subscriber);

}
