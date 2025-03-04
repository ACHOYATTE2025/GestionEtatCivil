package com.example.birthadvance.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "birthdoc")
public class ExtraitNaissance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numeroExtrait;
    private String nomComplet;
    private Date dateNaissance;
    private String lieuNaissance;
    private String nomPere;
    private String nomMere;
    private  String situationMatrimoniale;
    private String date;
    private Boolean Pactive=false;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account subscriber;



}
