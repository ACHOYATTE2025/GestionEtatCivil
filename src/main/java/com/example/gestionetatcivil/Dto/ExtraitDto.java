package com.example.birthadvance.Dto;

import java.util.Date;

public record ExtraitDto(
        Long id,
        String numeroExtrait,
        String nomComplet,
        Date dateNaissance,
        String lieuNaissance,
        String nomPere,
        String nomMere,
        String situationMatrimoniale,
        String date) {}
