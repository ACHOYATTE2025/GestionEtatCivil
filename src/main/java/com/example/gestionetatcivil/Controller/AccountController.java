package com.example.birthadvance.Controller;

import com.example.birthadvance.Dto.AccountAdminDto;
import com.example.birthadvance.Dto.AccountDto;
import com.example.birthadvance.Entities.Account;
import com.example.birthadvance.Entities.Validation;
import com.example.birthadvance.Repositories.AccountRepository;
import com.example.birthadvance.Repositories.ValidationRepository;
import com.example.birthadvance.Security.JwtService;
import com.example.birthadvance.Service.AccountService;
import com.example.birthadvance.Service.ContextSouscripteur;
import com.example.birthadvance.Service.NotificationService;
import com.example.birthadvance.Service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@RestController
@CrossOrigin("**")

public class AccountController {
    private final AccountService accountService;
    private final ContextSouscripteur contextSouscripteur;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private NotificationService notificationService;

    private ValidationRepository validationRepository;
    private AccountRepository accountRepository;
    private ValidationService validationService;
    private String Xcode;

    public AccountController(ValidationService validationService,   ValidationRepository validationRepository,   AuthenticationManager authenticationManager,AccountRepository accountRepository,  AccountService accountService, ContextSouscripteur contextSouscripteur,JwtService jwtService) {
        this.accountService = accountService;
        this.contextSouscripteur = contextSouscripteur;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.validationRepository = validationRepository;
        this.validationService = validationService;

    }

    // Register a susbscriber (EMPLOYEE/MANAGER) par l'admin
    @PostMapping(path = "registerAdmin")
    public void registerAdmin(@RequestBody Account subscriber) {
        this.accountService.registerAdmin(subscriber);
    }

    // Register a susbscriber par un USER
    @PostMapping(path = "register")
    public void register(@RequestBody Account subscriber) {
            this.accountService.register(subscriber);
    }


    //lire tous comptes
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
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
