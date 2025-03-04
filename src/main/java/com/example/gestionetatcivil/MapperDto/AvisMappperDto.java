package com.example.birthadvance.MapperDto;

import com.example.birthadvance.Dto.AvisDto;
import com.example.birthadvance.Entities.Avis;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

@Component
public class AvisMappperDto implements Function<Avis, AvisDto> {
    @Override
    public AvisDto apply(Avis avis) {
        return new AvisDto(avis.getId(),  avis.getNote(),avis.getValid(),avis.getDate() );
    }
}
