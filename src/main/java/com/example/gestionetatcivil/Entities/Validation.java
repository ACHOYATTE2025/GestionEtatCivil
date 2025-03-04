package com.example.birthadvance.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Instant creationCode;
    private Instant expirationCode;
    @ManyToOne(cascade = CascadeType.ALL)
    private Account subscriber;


}
