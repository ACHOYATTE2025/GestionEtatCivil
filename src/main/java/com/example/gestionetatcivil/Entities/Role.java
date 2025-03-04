package com.example.birthadvance.Entities;

import com.example.birthadvance.Enum.TypeRole;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public TypeRole libelle;

    public Role(int i, TypeRole typeRole) {
    }


}
