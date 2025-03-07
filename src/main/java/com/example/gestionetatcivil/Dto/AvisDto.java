package com.example.gestionetatcivil.Dto;

import java.util.Date;

public record AvisDto(
        Long id,
        String note,
        Boolean valid,
        Date date
) {
}
