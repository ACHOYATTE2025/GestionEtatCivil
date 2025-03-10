package com.example.gestionetatcivil.MapperDto;

import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import com.example.gestionetatcivil.Dto.AvisDto;
import com.example.gestionetatcivil.Entities.Avis;

@Component
public class AvisMappperDto implements Function<Avis, AvisDto> {
    @Override
    public AvisDto apply(Avis avis) {
        return new AvisDto(avis.getId(),  avis.getNote(),avis.getValid(),avis.getDate());
    }
}
