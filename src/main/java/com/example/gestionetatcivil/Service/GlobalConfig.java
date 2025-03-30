package com.example.gestionetatcivil.Service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.gestionetatcivil.Entities.ExtraitNaissance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@AllArgsConstructor
public class GlobalConfig {
  private Optional<ExtraitNaissance> documentLu;
  

}
