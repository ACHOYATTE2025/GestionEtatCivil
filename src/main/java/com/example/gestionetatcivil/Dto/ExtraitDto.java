package com.example.gestionetatcivil.Dto;

import java.util.Date;

public record ExtraitDto(
        Long id,
        String numeroExtrait,
        String nom,
        String prenoms,
        Date dateNaissance,
        String lieuNaissance,
        String nomPere,
        String nomMere,
        String situationMatrimoniale,
        String date) {}
