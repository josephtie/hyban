package com.nectux.mizan.hyban.parametrages.dto;


import com.nectux.mizan.hyban.parametrages.entity.RoleName;

public class UserDto {
    private String username;
    private String password;
    private String email;
    private String nomComplet;
    private RoleName roleName; // 🔹 Le rôle sous forme d'enum

    // Constructeurs
    public UserDto() {}

    public UserDto(String username, String password, String email, String nomComplet, RoleName roleName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nomComplet = nomComplet;
        this.roleName = roleName;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}

