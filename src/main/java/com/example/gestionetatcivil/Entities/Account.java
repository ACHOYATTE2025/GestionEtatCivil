package com.example.birthadvance.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;

    @OneToOne(cascade = CascadeType.ALL )
    private Role role;

    private Boolean active=false;
    private String choiX;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ExtraitNaissance> birthDoc;

    public Account(Account build) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.libelle.getAutorities();
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}



