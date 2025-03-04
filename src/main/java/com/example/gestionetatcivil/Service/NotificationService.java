package com.example.birthadvance.Service;

import com.example.birthadvance.Entities.Validation;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@NoArgsConstructor
public class NotificationService {
    private JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendNotification(Optional<Validation> validation) {
      /* SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom("birthsystem@gmail.com");
        message.setTo(validation.getSubscriber().getEmail());
        message.setSubject("Validation Code BirthSystem");
        message.setText(validation.getSubscriber().getUsername() +" votre code d'activation est :" +
                validation.getCode()+ " valable  10 mins");
        this.javaMailSender.send(message);*/
        log.info("code sent : " + validation.get().getCode());

    }
}
