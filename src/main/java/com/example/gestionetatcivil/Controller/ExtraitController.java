package com.example.gestionetatcivil.Controller;


import java.util.stream.Stream;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionetatcivil.Dto.AvisDto;
import com.example.gestionetatcivil.Dto.ExtraitDto;
import com.example.gestionetatcivil.Entities.Avis;
import com.example.gestionetatcivil.Entities.ExtraitNaissance;
import com.example.gestionetatcivil.Entities.Paiement;
import com.example.gestionetatcivil.Service.ExtraitService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class ExtraitController {

    private final ExtraitService birthDocService;


    public ExtraitController(ExtraitService birthDocService) {
        this.birthDocService = birthDocService;
    }


    // lire tous les extraits
    @GetMapping("/liredoc")
    @PreAuthorize("hasAnyAuthority('AMINISTRATOR,MANAGER,EMPLOYEE')")
    public Stream<ExtraitDto> liretousdoc(@RequestParam(required = false)  String numero) {
       return (Stream<ExtraitDto>) this.birthDocService.liretousdoc(numero);
    }


    // lire un extrait
    @GetMapping("/liredoc/{id}")
    @PreAuthorize("hasAnyAuthority('AMINISTRATOR,MANAGER,EMPLOYEE')")
    public Stream<ExtraitDto> lireundocument(@PathVariable Long id) {
        return this.birthDocService.lireundocument(id);

    }

    // creation d'extrait
    @PreAuthorize("hasAnyAuthority('USER,EMPLOYEE,ADMINISTRATOR')")
    @PostMapping("/creationdoc")
    public void creationdocument(@RequestBody ExtraitNaissance birth) {
        this.birthDocService.creationdocument(birth);
    }

    //paiment des frais d'estrait de naissance
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/paiement")
    public boolean paiementdoc(Paiement paiement) {
        return this.birthDocService.paiementDoc(paiement);

    }

    //creer un avis sur un extrait
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ADMINISTRATOR')")
    @PostMapping("/avisdoc")
    public void avisdocument(@RequestBody Avis noted)  {
        this.birthDocService.avisdocument(noted); }


    //lire les avis
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("/lireavis")
    public Stream<AvisDto> readavis()   {
       return this.birthDocService.readavis();}


    //lire un avis
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("/lireavis/{id}")
    public Stream<AvisDto>  Readunavis(@PathVariable Long id)   {
        return  this.birthDocService.readunavis(id); }
}

