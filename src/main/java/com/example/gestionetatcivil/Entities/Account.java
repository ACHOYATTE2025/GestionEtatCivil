package com.example.gestionetatcivil.Entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @Lob
    private Byte[] photo_Id;
    
    @Lob
    private Byte[] cni_recto;

    @Lob
    private Byte[] cni_verso;
    

    @OneToOne(cascade = CascadeType.ALL )
    private Role role;

    private Boolean active=false;
    private String choiX="";

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ExtraitNaissance> birthDoc;

    public Account(Account build) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getLibelle().getAutorities();
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


    public void setPhoto_Id(byte[] bytes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPhoto_Id'");
    }


    public void setCni_recto(byte[] bytes) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setCni_recto'");
    }


    public void setCni_verso(byte[] bytes) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setCni_verso'");
    }
}



