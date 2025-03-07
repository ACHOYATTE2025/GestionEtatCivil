package com.example.gestionetatcivil.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numeroPaiment;
    long paiementAmount;
    Instant paiementDate;
    Boolean activated= false;

    @OneToOne(cascade = CascadeType.ALL)
    private ExtraitNaissance birthDocument;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;
}
