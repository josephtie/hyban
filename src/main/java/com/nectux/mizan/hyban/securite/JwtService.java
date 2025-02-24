//package com.nectux.mizan.hyban.securite;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class JwtService {
//
//    private final String SECRET_KEY = "mysecretkey";  // Clé secrète utilisée pour signer le JWT
//    private final Long EXPIRATION_TIME = 86400000L;  // Durée d'expiration du token (1 jour en ms)
//    private final UserDetailsService userDetailsService; // Injecter votre UserDetailsService
//
//    public JwtService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Définit la date d'expiration
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Utilise la clé secrète pour signer le token
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);  // Vérifie la signature du token
//            return true;
//        } catch (Exception e) {
//            return false;  // Si le token est invalide, retourne false
//        }
//    }
//
//    public Authentication getAuthentication(String token) {
//        // Extraire le nom d'utilisateur du token
//        String username = extractUsername(token);
//
//        // Charger les détails de l'utilisateur via le UserDetailsService
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        // Retourner un token d'authentification
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//
//    // Méthode utilitaire pour extraire le nom d'utilisateur du token JWT
//    private String extractUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();  // Retourne le nom d'utilisateur
//    }
//}
//
