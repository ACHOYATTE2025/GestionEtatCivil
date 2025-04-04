package com.example.gestionetatcivil.Entities;

import java.sql.Date;

import com.example.gestionetatcivil.Enum.TypeSexe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @Column(nullable=false)
    private String numeroExtrait;
    @Column(nullable=false)
    private String nom;
    @Column(nullable=false)
    private String prenoms;
    @Column(nullable=false)
    private Date dateNaissance;
    @Column(nullable=false)
    private String lieuNaissance;
    @Column(nullable=false)
    private TypeSexe sexe;
    @Column(nullable=false)
    private String nomPere;
    @Column(nullable=false)
    private String nomMere;
    @Column(nullable=false)
    private  String situationMatrimoniale;
    @Column(nullable=false)
    private String date_extrait;
    private Boolean Pactive=false;

    @ManyToOne()
    private Account subscriber;

    
}