package com.example.gestionetatcivil.Dto;

import java.util.Date;

import com.example.gestionetatcivil.Entities.ExtraitNaissance;

public record AvisDto(
        Long id,
        String note,
        Boolean valid,
        Date date
        
) {
}
