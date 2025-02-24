//package com.nectux.mizan.hyban.securite;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtAuthenticationProvider implements AuthenticationProvider {
//
//    private final JwtService jwtService;
//
//    public JwtAuthenticationProvider(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String token = (String) authentication.getCredentials();  // Récupère les informations d'authentification (ici, le token JWT)
//
//        if (jwtService.validateToken(token)) {  // Si le token est valide
//            return jwtService.getAuthentication(token);  // Récupère l'authentification à partir du token
//        }
//        throw new BadCredentialsException("Invalid token");  // Si le token est invalide, jette une exception
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);  // Le provider prend en charge les tokens UsernamePasswordAuthenticationToken
//    }
//}
//
