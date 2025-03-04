package com.example.birthadvance.Controller;

import com.example.birthadvance.Dto.AvisDto;
import com.example.birthadvance.Dto.ExtraitDto;
import com.example.birthadvance.Entities.Avis;
import com.example.birthadvance.Entities.ExtraitNaissance;
import com.example.birthadvance.Entities.Paiement;
import com.example.birthadvance.Service.ExtraitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@Slf4j

public class ExtraitController {

    private final ExtraitService birthDocService;
    ExtraitDto extraitDto;

    public ExtraitController(ExtraitService birthDocService) {
        this.birthDocService = birthDocService;
    }


    // lire tous les extraits
    @GetMapping("/liredoc")
    @PreAuthorize("hasAnyAuthority('AMINISTRATOR,MANAGER,EMPLOYEE')")
    public List<ExtraitDto> liretousdoc(@RequestParam(required = false)  String numeroExtrait) {
       return (List<ExtraitDto>) this.birthDocService.liretousdoc(numeroExtrait);
    }


    // lire un extrait
    @GetMapping("/liredoc/{id}")
    @PreAuthorize("hasAnyAuthority('AMINISTRATOR,MANAGER,EMPLOYEE')")
    public Stream<ExtraitDto> lireundocument(@PathVariable Long id) {
        return this.birthDocService.lireundocument(id);

    }

    // creation d'extrait
    @PreAuthorize("hasAnyAuthority('EMPLOYEE,ADMINISTRATOR')")
    @PostMapping("/creationdoc")
    public void creationdocument(@RequestBody ExtraitNaissance birth) throws Exception {
        this.birthDocService.creationdocument(birth);
    }

    //paiment des frais d'estrait de naissance
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/paiement")
    public boolean paiementdoc(Paiement paiement) throws Exception {
        return this.birthDocService.paiementDoc(paiement);

    }

    //creer un avis sur un extrait
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ADMINISTRATOR')")
    @PostMapping("/avisdoc")
    public void avisdocument(@RequestBody Avis noted)  throws Exception {
        this.birthDocService.avisdocument(noted); }


    //lire les avis
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("/lireavis")
    public Stream<AvisDto> readavis()  throws Exception {
       return this.birthDocService.readavis();}


    //lire un avis
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("/lireavis/{id}")
    public Stream<AvisDto>  Readunavis(@PathVariable Long id)  throws Exception {
        return  this.birthDocService.readunavis(id); }
}

