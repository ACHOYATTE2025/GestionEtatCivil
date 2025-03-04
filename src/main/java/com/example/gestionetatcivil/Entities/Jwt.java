package com.example.birthadvance.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "jwt")
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String valeur;
    private Boolean desactive=false;
    private Boolean expiration=false;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE})
    private Account subscriber;
}
