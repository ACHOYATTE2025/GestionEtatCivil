package com.example.gestionetatcivil.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypePermission {
    ADMINISTRATOR_CREATE,
    ADMINISTRATOR_READ,
    ADMINISTRATOR_UPDATE,
    ADMINISTRATOR_DELETE,

    MANAGER_CREATE,
    MANAGER_READ,
    MANAGER_UPDATE,
    MANAGER_DELETE,

    EMPLOYEE_CREATE,
    EMPLOYEE_READ,
    EMPLOYEE_UPDATE,


    USER_CREATE;

    private String libelle;
}
