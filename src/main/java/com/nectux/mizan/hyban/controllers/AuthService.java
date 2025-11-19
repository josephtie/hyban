package com.nectux.mizan.hyban.controllers;




import com.nectux.mizan.hyban.parametrages.dto.AuthResponse;
import com.nectux.mizan.hyban.parametrages.dto.LoginRequest;
import com.nectux.mizan.hyban.parametrages.dto.ProfileDto;
import com.nectux.mizan.hyban.parametrages.dto.RegisterRequest;
import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.RoleName;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRoleRepository;
import com.nectux.mizan.hyban.securite.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurRoleRepository utilisateurRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        // Vérifier si le username existe déjà
        if(utilisateurRepository.findByUsername(request.getUsername()) != null){
            throw new RuntimeException("Username already exists");
        }

        // Créer un nouvel utilisateur
        Utilisateur user = new Utilisateur();
        UtilisateurRole userRole = new UtilisateurRole();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNomComplet(request.getNomComplet());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Récupérer le rôle USER
        Optional<Role> role = roleRepository.findByName(RoleName.USER);

        if(role != null){
            // Créer l'association UtilisateurRole
            UtilisateurRole ur = new UtilisateurRole();
            ur.setUtilisateur(user);
            ur.setRole(role.get());

            // Ajouter à la liste des roles de l'utilisateur
            user.getUtilisateurRoles().add(ur);
        }

        // Sauvegarder l'utilisateur
        user=utilisateurRepository.save(user);
        userRole.setUtilisateur(user);
        userRole.setRole(role.get());
        utilisateurRoleRepository.save(userRole);
        // Générer le token JWT
        String token = jwtUtils.generateJwtToken(user);

        return new AuthResponse(token, "");
    }


    public AuthResponse login(LoginRequest request) {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(request.getUsername());
        if(user.get() == null || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtils.generateJwtToken(user.get());
        return new AuthResponse(token, "");
    }

    public ProfileDto profile(String username){
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
        ProfileDto dto = new ProfileDto();
        dto.setUsername(user.get().getUsername());
        dto.setEmail(user.get().getEmail());
        dto.setNomComplet(user.get().getNomComplet());
        return dto;
    }
}

