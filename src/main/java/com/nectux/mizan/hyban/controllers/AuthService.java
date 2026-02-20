package com.nectux.mizan.hyban.controllers;



import com.nectux.mizan.hyban.parametrages.dto.AuthResponse;
import com.nectux.mizan.hyban.parametrages.dto.LoginRequest;
import com.nectux.mizan.hyban.parametrages.dto.ProfileDto;
import com.nectux.mizan.hyban.parametrages.dto.RegisterRequest;
import com.nectux.mizan.hyban.parametrages.dto.RefreshRequest;
import com.nectux.mizan.hyban.parametrages.dto.ForgotPasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.ResetPasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.ChangePasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.UpdateProfileRequest;
import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.RoleName;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRoleRepository;
import com.nectux.mizan.hyban.securite.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    // Simple in-memory blacklist (token -> expiry)
    private final Map<String, Instant> tokenBlacklist = new HashMap<>();

    public AuthResponse register(RegisterRequest request) {
        // Vérifier si le username existe déjà
        Optional<Utilisateur> existing = utilisateurRepository.findByUsername(request.getUsername());
        if(existing.isPresent()){
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

        if(role.isPresent()){
            // Créer l'association UtilisateurRole
            UtilisateurRole ur = new UtilisateurRole();
            ur.setUtilisateur(user);
            ur.setRole(role.get());

            // Ajouter à la liste des roles de l'utilisateur
            user.getUtilisateurRoles().add(ur);
        }

        // Sauvegarder l'utilisateur
        user=utilisateurRepository.save(user);
        if(role.isPresent()){
            userRole.setUtilisateur(user);
            userRole.setRole(role.get());
            utilisateurRoleRepository.save(userRole);
        }
        // Générer le token JWT
        String token = jwtUtils.generateJwtToken(user);

        return new AuthResponse(token, "");
    }


    public AuthResponse login(LoginRequest request) {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(request.getUsername());
        if(user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtils.generateJwtToken(user.get());
        return new AuthResponse(token, "");
    }

    public ProfileDto profile(String username){
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
        if(user.isEmpty()) throw new RuntimeException("User not found");
        ProfileDto dto = new ProfileDto();
        dto.setUsername(user.get().getUsername());
        dto.setEmail(user.get().getEmail());
        dto.setNomComplet(user.get().getNomComplet());
        List<String> roles = user.get().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        dto.setRole(roles);
        return dto;
    }

    // --- Nouveaux endpoints ---

    public AuthResponse refresh(RefreshRequest request){
        String refresh = request.getRefreshToken();
        if(refresh == null || !jwtUtils.validateJwtToken(refresh)){
            throw new RuntimeException("Invalid refresh token");
        }
        String username = jwtUtils.getUsernameFromJwt(refresh);
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
        if(user.isEmpty()) throw new RuntimeException("User not found");
        String newAccess = jwtUtils.generateJwtToken(user.get());
        return new AuthResponse(newAccess, refresh);
    }

    public void logout(String token){
        if(token == null) return;
        // Try to determine expiry by validating token; we don't have direct expiry, so set a reasonable TTL (e.g., 1 hour)
        Instant expiry = Instant.now().plus(1, ChronoUnit.HOURS);
        tokenBlacklist.put(token, expiry);
    }

    public boolean verifyToken(String token){
        if(token == null) return false;
        if(tokenBlacklist.containsKey(token)) return false;
        return jwtUtils.validateJwtToken(token);
    }

    public void forgotPassword(ForgotPasswordRequest request){
        String email = request.getEmail();
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if(user == null) return; // ne pas révéler l'existence
        String token = UUID.randomUUID().toString();
        // Stocker le token et expiry dans des colonnes additionnelles de Utilisateur
        // Utilisateur n'a pas encore ces champs, on utilisera des propriétés temporaires via map (quick solution)
        // Pour la simplicité ici, on va surcharger le nom complet pour stocker token|expiry - IDEAL: ajouter champs en base
        String baseName = user.getNomComplet() == null ? "" : user.getNomComplet();
        user.setNomComplet(baseName + "::RESET::" + token + "::" + Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());
        utilisateurRepository.save(user);
        // TODO: envoyer email avec le token (stub)
    }

    public void resetPassword(ResetPasswordRequest request){
        String token = request.getToken();
        String newPassword = request.getNewPassword();
        // Rechercher utilisateur par scan du nomComplet (quick hack)
        List<Utilisateur> all = utilisateurRepository.findAll();
        for(Utilisateur u : all){
            String nc = u.getNomComplet();
            if(nc != null && nc.contains("::RESET::" + token + "::")){
                // vérifier expiry
                String[] parts = nc.split("::RESET::");
                if(parts.length >= 2){
                    String[] tail = parts[1].split("::");
                    if(tail.length >= 2){
                        long expiry = Long.parseLong(tail[1]);
                        if(Instant.now().toEpochMilli() > expiry) throw new RuntimeException("Token expired");
                        u.setPassword(passwordEncoder.encode(newPassword));
                        // nettoyer le nomComplet pour retirer le token
                        u.setNomComplet(parts[0]);
                        utilisateurRepository.save(u);
                        return;
                    }
                }
            }
        }
        throw new RuntimeException("Invalid token");
    }

    public void changePassword(String username, ChangePasswordRequest request){
        if(username == null) throw new RuntimeException("Unauthorized");
        Optional<Utilisateur> userOpt = utilisateurRepository.findByUsername(username);
        if(userOpt.isEmpty()) throw new RuntimeException("User not found");
        Utilisateur u = userOpt.get();
        if(!passwordEncoder.matches(request.getOldPassword(), u.getPassword())){
            throw new RuntimeException("Old password does not match");
        }
        u.setPassword(passwordEncoder.encode(request.getNewPassword()));
        utilisateurRepository.save(u);
    }

    public ProfileDto updateProfile(String username, UpdateProfileRequest request){
        if(username == null) throw new RuntimeException("Unauthorized");
        Optional<Utilisateur> userOpt = utilisateurRepository.findByUsername(username);
        if(userOpt.isEmpty()) throw new RuntimeException("User not found");
        Utilisateur u = userOpt.get();
        if(request.getEmail() != null) u.setEmail(request.getEmail());
        if(request.getNomComplet() != null) u.setNomComplet(request.getNomComplet());
        utilisateurRepository.save(u);
        return profile(username);
    }
}
