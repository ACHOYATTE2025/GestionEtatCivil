package com.example.birthadvance.Enum;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public enum TypeRole {
    USER(
            Set.of( TypePermission.USER_CREATE)
    ),
    EMPLOYEE(
            Set.of(TypePermission.EMPLOYEE_CREATE, TypePermission.EMPLOYEE_READ,TypePermission.EMPLOYEE_UPDATE)
    ),
    ADMINISTRATOR(
            Set.of(TypePermission.ADMINISTRATOR_CREATE, TypePermission.ADMINISTRATOR_UPDATE,
                    TypePermission.ADMINISTRATOR_DELETE, TypePermission.ADMINISTRATOR_READ)
    ),
    MANAGER(
            Set.of(TypePermission.MANAGER_CREATE, TypePermission.MANAGER_READ,TypePermission.MANAGER_UPDATE,
                    TypePermission.MANAGER_DELETE)
    );

    // set Role and Permission
    final
    Set<TypePermission> permissions;

    public Collection<? extends GrantedAuthority> getAutorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new java.util.ArrayList<>(this.getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.name())
        ).toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
