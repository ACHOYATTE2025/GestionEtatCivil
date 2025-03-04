package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.Account;
import com.example.birthadvance.Entities.Validation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {
     Optional<Validation> findByCode(String code);
     Optional<Validation> deleteByCode(String code);
     Optional<Validation> findById(Long id);
     Optional<Validation> findBySubscriber(Account subscriber);

}
