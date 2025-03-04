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
@Table(name = "avisdoc")
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title="Notes rélatives à l'extrait de naissance";
    private String note;
    Boolean valid=false ;
    private Date date;


    @OneToOne(cascade = CascadeType.MERGE)
    private ExtraitNaissance birthDoc;



}
