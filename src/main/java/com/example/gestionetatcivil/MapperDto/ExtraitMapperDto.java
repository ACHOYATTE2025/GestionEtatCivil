package com.example.gestionetatcivil.MapperDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.gestionetatcivil.Dto.ExtraitDto;
import com.example.gestionetatcivil.Entities.ExtraitNaissance;

@Component
public class ExtraitMapperDto  implements Function<ExtraitNaissance, ExtraitDto> {



    @Override
    public ExtraitDto apply(ExtraitNaissance extrait) {
        return new ExtraitDto(extrait.getId(), extrait.getNumeroExtrait(), extrait.getNom(),extrait.getPrenoms(),
                extrait.getDateNaissance(),extrait.getLieuNaissance(), extrait.getNomPere(),
                extrait.getNomMere(), extrait.getSituationMatrimoniale(),extrait.getDate_extrait());
    }
}
