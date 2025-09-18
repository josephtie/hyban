package com.nectux.mizan.hyban.parametrages.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private String username;
    private String email;
    private String nomComplet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
