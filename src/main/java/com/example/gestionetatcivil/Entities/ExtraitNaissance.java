package com.example.gestionetatcivil.Entities;

import java.sql.Date;

import com.example.gestionetatcivil.Enum.TypeSexe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "extraitnaissance")
public class ExtraitNaissance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numeroExtrait;
    private String nom;
    private String prenoms;
    private Date dateNaissance;
    private String lieuNaissance;
    private TypeSexe sexe;
    private String nomPere;
    private String nomMere;
    private  String situationMatrimoniale;
    private String date;
    private Boolean Pactive=false;

    @ManyToOne()
    private Account subscriber;

    
}