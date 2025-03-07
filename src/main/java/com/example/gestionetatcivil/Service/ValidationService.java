package com.example.gestionetatcivil.Service;

import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Validation;
import com.example.gestionetatcivil.Repositories.ValidationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service

public class ValidationService {
    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;
    public String codeX ="";
    public Account accountX = new Account();

    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;


    }



    //code making
    public void createCode(Account subscriber) {

        Validation validation = new Validation();
        validation.setSubscriber(subscriber);
        this.accountX=subscriber;


        Instant now = Instant.now();
        validation.setCreationCode(now);
        validation.setExpirationCode(now.plus(10, ChronoUnit.MINUTES));


        Random random = new Random();
        random.nextInt(999999);
        String code = String.valueOf(random.nextInt(999999));
        validation.setCode(code);
        this.codeX = code;

        this.validationRepository.save(validation);
        this.notificationService.sendNotification(validation);

    }

    //verifiez la validation du code
    public Validation getValidation(String code) {
       return this.validationRepository.findByCode(code).orElseThrow(()-> new RuntimeException("Invalid code"));

    };

}
