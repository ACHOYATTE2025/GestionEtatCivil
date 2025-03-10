package com.example.gestionetatcivil.Repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionetatcivil.Entities.ExtraitNaissance;








public interface ExtraitRepository extends JpaRepository<ExtraitNaissance, Long> {
   
   
   public Optional<ExtraitNaissance> findByNumeroExtrait(String numeroExtrait);
  
   public ExtraitNaissance  findById(long id);

}
