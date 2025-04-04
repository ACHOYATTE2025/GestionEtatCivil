package com.example.gestionetatcivil;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Role;
import com.example.gestionetatcivil.Enum.TypeRole;
import com.example.gestionetatcivil.Repositories.AccountRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SpringBootApplication
@AllArgsConstructor
@Getter
@Setter
public class GestionEtatCivilApplication implements CommandLineRunner {
    @Autowired    
 
    AccountRepository SubscriberRepository;
    PasswordEncoder passwordEncoder;


    public static void main(String[] args){
        SpringApplication.run(GestionEtatCivilApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
           //Administrator making
        Account admin = Account.builder()
                .active(true)
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(new Role(0,TypeRole.ADMINISTRATOR)
                )
                .email("admin@gmail.com")
                .phone("0748365619")
                .choix("")
                .build();
        admin = this.SubscriberRepository.findByEmail(admin.getEmail())
                .orElse(admin);
        this.SubscriberRepository.save(admin);

    // Manager making
        Account manager = Account.builder()
                .active(true)
                .username("manager")
                .password(passwordEncoder.encode("manager"))
                .role(
                        new Role(0,TypeRole.MANAGER)

                )
                .email("managerBirth@gmail.com")
                .phone("0574803332")
                .choix("")
                .build();
        manager = this.SubscriberRepository.findByEmail(manager.getEmail())
                .orElse(manager);
        this.SubscriberRepository.save(manager);
    }

}
