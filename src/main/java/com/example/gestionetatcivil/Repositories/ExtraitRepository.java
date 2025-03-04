package com.example.birthadvance.Repositories;

import com.example.birthadvance.Entities.ExtraitNaissance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraitRepository extends JpaRepository<ExtraitNaissance, Long> {

   public ExtraitNaissance findByNumeroExtrait(String numeroExtrait);


}
