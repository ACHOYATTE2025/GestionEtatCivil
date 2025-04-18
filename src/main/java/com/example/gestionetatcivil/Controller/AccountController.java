package com.example.gestionetatcivil.Controller;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestionetatcivil.Dto.AccountAdminDto;
import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Validation;
import com.example.gestionetatcivil.Repositories.AccountRepository;
import com.example.gestionetatcivil.Repositories.ValidationRepository;
import com.example.gestionetatcivil.Security.JwtService;
import com.example.gestionetatcivil.Service.AccountService;
import com.example.gestionetatcivil.Service.ContextSouscripteur;
import com.example.gestionetatcivil.Service.ValidationService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin("**")

public class AccountController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final ValidationService validationService;
    private String Xcode;

    public AccountController(ValidationService validationService,   ValidationRepository validationRepository,   AuthenticationManager authenticationManager,AccountRepository accountRepository,  AccountService accountService, ContextSouscripteur contextSouscripteur,JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.validationService = validationService;

    }

    // Register a susbscriber (EMPLOYEE/MANAGER) par l'admin
    @PostMapping(path = "registerAdmin")
    public void registerAdmin(@RequestBody Account subscriber) {
        this.accountService.registerAdmin(subscriber);
    }

    // Register a susbscriber par un USER
    @PostMapping(path = "register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@RequestParam  String username,
                                           @RequestParam  String password,
                                           @RequestParam String phone,
                                           @RequestParam String email,
                                           @RequestParam(value = "images") MultipartFile[] images ) throws IOException {
            try {
                
                 this.accountService.register(username,password,phone,email,images);
                 System.out.println("nombre d'images recues: "+ (images !=null?images.length:"null"));
            return ResponseEntity.ok("Inscription Reussie : " );
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'incription.");
        }
    }
    // lire image du compte ACCOUNT compte pour tester
    @GetMapping("/{id}/image/{index}")
    public ResponseEntity<byte[]> getAccount(@PathVariable Long id,@PathVariable int index) {
        Optional <Account> accountx = this.accountService.ObtenirAcount(id);
        if( accountx.isEmpty()){return null;}
        byte[][] images = accountx.get().getImages();
                
        
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(images[index]);
       
    }
    

    //lire compte pour tester
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccount(@PathVariable Long id) {
        Optional <Account> accountx = this.accountService.ObtenirAcount(id);
        if( accountx.isEmpty()){return null;}
                     
        
        return ResponseEntity.ok().body(accountx);
       
    }




    //lire tous comptes
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','EMPLOYEE')")
    @GetMapping("/lirecomptes")
    public Stream<AccountAdminDto> getaAccount(@RequestParam(required = false) String compt) throws Exception {
        return this.accountService.getAccount(compt); }

    //activation
    @PostMapping(path = "activation")
    public void Activation(@RequestBody Map<String,String> activation) {
        this.accountService.activation(activation);
    }


    //renvoi code d'activation
    @PostMapping(path = "reactived")
    public void reactivationCompte(@RequestBody Map<String,String> reactived) {
        this.accountService.renvoiCode(reactived);
    }


    //connexion double facteur
    @PostMapping(path = "login")
    public Map<String,String> login(@RequestBody AccountAdminDto subscriberDto ) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        subscriberDto.email(), subscriberDto.password()));
        if (authenticate.isAuthenticated()) {
           this.Xcode=subscriberDto.email();
            Optional<Account> byEmail = this.accountRepository.findByEmail(subscriberDto.email());
            this.validationService.createCode(byEmail.get());
        }
        return null;
    }

    //Activation connexion double facteur
    @PostMapping(path = "activationlogin")
    public Map<String,String> activationLogin(@RequestBody  Map<String,String> activation ) {
        String code = activation.get("code");
        Validation validationX = this.validationService.getValidation(code);
        if(validationX==null) {throw new RuntimeException("code not found");}
        if(!validationX.getCode().equals(code)){
            throw new RuntimeException("code Invalid");
        }
        if(Instant.now().isAfter(validationX.getExpirationCode())){
            throw new RuntimeException("expiration Code Invalid");
        }

        return this.jwtService.generateToken(validationX.getSubscriber().getEmail());
    }





    //modifier mot de passe
    @ResponseStatus(value = HttpStatus.FOUND)
    @PostMapping(path = "resetpassword")
    public void ModifierMotDePasse(@RequestBody Map<String,String> UpdateMotDePasse) throws Throwable {
        this.accountService.resetpassword(UpdateMotDePasse);
    }

    //nouveau mot de passe
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "newpassword")
    public void newpassword(@RequestBody Map<String,String> NouveauMotDePasse) throws Throwable {
        this.accountService.newpassword(NouveauMotDePasse);
    }

    //deconnexion
    @PostMapping(path = "deconex")
    public void deconex()  {
        this.jwtService.deconex();
    }

    //desactiver un souscripteur
    @PostMapping("desactiversouscripteur")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public void deletesouscripteur(@RequestBody Map<String,String> emailSouscripteur) throws Exception {
            this.accountService.deletesouscripteur( emailSouscripteur);}



}
