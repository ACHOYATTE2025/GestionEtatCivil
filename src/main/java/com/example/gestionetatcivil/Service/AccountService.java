package com.example.gestionetatcivil.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gestionetatcivil.Dto.AccountAdminDto;
import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Role;
import com.example.gestionetatcivil.Entities.Validation;
import com.example.gestionetatcivil.Enum.TypeRole;
import com.example.gestionetatcivil.MapperDto.AccountMapperDto;
import com.example.gestionetatcivil.Repositories.AccountRepository;
import com.example.gestionetatcivil.Repositories.ValidationRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final ValidationRepository validationRepository;
    private final AccountMapperDto accountMapperDto;
    private PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private ContextSouscripteur contextSouscripteur;




    // Register a susbscriber (EMPLOYEE/MANAGER) par l'admin
    public void registerAdmin(Account subscriber) {
        Optional<Account> sub =  contextSouscripteur.getSouscripteur();
        log.info("A VERIFIER  :"+sub.get().getUsername());
        //check if he exists already
        if (accountRepository.findByEmail(sub.get().getEmail()).isPresent()) {
            throw new RuntimeException("Subscriber already exists");
        }
        //check the right email
        if (!subscriber.getEmail().contains(".") || !subscriber.getEmail().contains("@")) {
            throw new RuntimeException("Invalid email format");
        }
        // make the role
        Role role = new Role();
        Role roleX = sub.get().getRole();
        if (roleX.getLibelle().equals(TypeRole.ADMINISTRATOR)) {
            if (subscriber.getChoiX().equals("EMPLOYEE")) {
                role.setLibelle(TypeRole.EMPLOYEE);
            } else {
                role.setLibelle(TypeRole.MANAGER);
            }
        } else {
            role.setLibelle(TypeRole.USER);
        }

        subscriber.setRole(role);

        //crypt password
        String mdp = passwordEncoder.encode(subscriber.getPassword());
        subscriber.setPassword(mdp);
        this.validationService.createCode(subscriber);
            this.accountRepository.save(subscriber);

    }
/* ************************************************************************************************ */

    // Register a susbscriber par un USER
    public void register(Account subscriber) {
        //check if he exists already
        if (accountRepository.findByEmail(subscriber.getEmail()).isPresent()) {
            throw new RuntimeException("Subscriber already exists");
        }
        //check the right email
        if (!subscriber.getEmail().contains(".") || !subscriber.getEmail().contains("@")) {
            throw new RuntimeException("Invalid email format");
        }
        // make the role
        Role role = new Role();
        role.setLibelle(TypeRole.USER);
        subscriber.setRole(role);

        //crypt password
        String mdp = passwordEncoder.encode(subscriber.getPassword());
        subscriber.setPassword(mdp);
        this.validationService.createCode(subscriber);
        this.accountRepository.save(subscriber);

    }


/* *****************************************************************************************************/


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }


    //Activation
    public void activation(Map<String, String> activation) {
        Validation codex = this.validationService.getValidation(activation.get("code"));
        if (Instant.now().isAfter(codex.getExpirationCode())) {
            throw new RuntimeException("Code expired");
        }
        Account subscriberActivatedorNot = this.accountRepository.findByEmail(codex.getSubscriber().getEmail()).
                orElseThrow(() -> new RuntimeException("Subscriber not found"));
        subscriberActivatedorNot.setActive(true);
        this.accountRepository.save(subscriberActivatedorNot);

    }
/* ***************************************************************************************************/
    //renvoi de  code d'activation de compte
    public void renvoiCode(Map<String, String> reactived) {
        Account subscriber = (Account) this.loadUserByUsername(reactived.get("email"));
        this.validationService.createCode(subscriber);

    }
/***************************************************************************************************/

    //modifier mot de passe
    public void resetpassword(Map<String, String> UpdateMotDePasse) {
        Account subscriber = (Account) this.loadUserByUsername(UpdateMotDePasse.get("email"));
        log.info(subscriber.getUsername());
        this.validationService.createCode(subscriber);
    }

/**************************************************************************************************/
    //nouveau mot de passe
    public void newpassword(Map<String, String> NouveauMotDePasse)  {
        Account subscriber = (Account) this.loadUserByUsername(NouveauMotDePasse.get("email"));
        final Optional<Validation> code = Optional.ofNullable(validationService.getValidation(NouveauMotDePasse.get("code")));
        if (code.isPresent()) {
            String mdp = passwordEncoder.encode(NouveauMotDePasse.get("password"));
            subscriber.setPassword(mdp);

            this.accountRepository.save(subscriber);
        }


    }
/*******************************************************************************************************/
    //liste de tous les utilsateurs uniquement par l'Admionistrateur
    public Stream<AccountAdminDto> getAccount(String email) {
        boolean notEmpty = Strings.isNotEmpty(email);
        if(notEmpty){this.accountRepository.findAll().stream().map(accountMapperDto);}
       return this.accountRepository.findAll().stream().map(accountMapperDto);
    }
/*********************************************************************************************************/
    //desactiver souscripteur
    public void deletesouscripteur(Map<String, String> emailSouscripteur) throws Exception {
        String email = emailSouscripteur.get("email");
        Account souscripteur = this.accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Subscriber not found"));
        souscripteur.setActive(false);
        this.accountRepository.save(souscripteur);
    }
/*********************************************************************************************************/
    //supprimer les comptes desactivés par semaine
    @Scheduled(cron = "@weekly")
    public void removeUselessSubscriber() {
        Iterable<Validation> val = this.validationRepository.findAll();
        for(Validation v : val) {
            if (Instant.now().isAfter(v.getExpirationCode())) {
                this.validationRepository.delete(v);}

            if (!v.getSubscriber().getActive()) {
                this.accountRepository.delete(v.getSubscriber());}
        }
    }

    // activation login (connexion double facteur)
    //

    }
