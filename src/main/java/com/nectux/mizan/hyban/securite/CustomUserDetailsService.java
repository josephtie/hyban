//package com.nectux.mizan.hyban.securite;
//
//import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UtilisateurRepository userRepository;
//
//    public CustomUserDetailsService(UtilisateurRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        user.getAuthorities() // Utilisation des autorités correctes
//                ))
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//
//}
