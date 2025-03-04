package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PaiementRepository extends JpaRepository<Paiement,Integer> {

}
