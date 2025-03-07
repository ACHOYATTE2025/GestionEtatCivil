package com.example.gestionetatcivil.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionetatcivil.Entities.ExtraitNaissance;







public interface ExtraitRepository extends JpaRepository<ExtraitNaissance, Long> {
   
   String findByNumeroExtrait = null;
   public ExtraitNaissance findByNumeroExtrait(String numeroExtrait);
   public ExtraitNaissance  findById(long id);

}
