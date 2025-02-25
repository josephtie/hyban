package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Component("utilisateur")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_UTILISATEUR")
@SequenceGenerator(name="CGECI_RHPAIE_UTILISATEUR_SEQUENCE", sequenceName="CGECI_RHPAIE_UTILISATEUR_SEQ", initialValue=1, allocationSize=1)
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_UTILISATEUR_SEQUENCE")
    @Column(unique=true, nullable=false)
    private Long id;



        @Column(unique = true, nullable = false)
        private String username;

        @Column(nullable = false)
        private String password;

        @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JsonIgnore
        private List<UtilisateurRole> utilisateurRoles = new ArrayList<>();

        @Column(unique = true, nullable = false)
        private String email;

        private String nomComplet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utilisateurRoles.stream()
                .map(utilisateurRole -> new SimpleGrantedAuthority(utilisateurRole.getRole().getName().name()))
                .collect(Collectors.toList());
    }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        // Getters et Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UtilisateurRole> getUtilisateurRoles() {
        return utilisateurRoles;
    }

    public void setUtilisateurRoles(List<UtilisateurRole> utilisateurRoles) {
        this.utilisateurRoles = utilisateurRoles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
