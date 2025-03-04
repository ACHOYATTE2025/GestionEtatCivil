package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AvisRepository extends JpaRepository<Avis, Long> {
}
