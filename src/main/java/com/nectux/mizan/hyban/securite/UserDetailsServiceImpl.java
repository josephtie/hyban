package com.nectux.mizan.hyban.securite;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
//import com.nectux.mizan.hyban.parametrages.repository.UserRepository;
import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository userRepository;
    private final UtilisateurRoleRepository utilisateurRoleRepository;

    @Autowired
    public UserDetailsServiceImpl(UtilisateurRepository userRepository, UtilisateurRoleRepository utilisateurRoleRepository) {
        this.userRepository = userRepository;
        this.utilisateurRoleRepository = utilisateurRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //  Récupérer les rôles explicitement
        List<UtilisateurRole> utilisateurRoles = utilisateurRoleRepository.findByUtilisateurUsername(user.getUsername());
        user.setUtilisateurRoles(utilisateurRoles);

        //  Vérifier que les rôles ne commencent pas déjà par "ROLE_"
        //  Utiliser .authorities() au lieu de .roles()
        List<String> authorities = utilisateurRoles.stream()
                .map(utilisateurRole -> {
                    String roleName = utilisateurRole.getRole().getName().name();
                    return roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
                })
                .toList();

        //  Log pour vérifier si les rôles sont bien chargés
        System.out.println("🟢 Utilisateur trouvé: " + user.getUsername());
        System.out.println("🟢 Authorities: " + authorities);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities.toArray(new String[0])) //  Utiliser .authorities() au lieu de .roles()
                .build();
    }

        //System.out.println(" Utilisateur trouvé: " + User);

}

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UtilisateurRepository userRepository;
//    private UtilisateurRoleRepository utilisateurRoleRepository;
//
//    public UserDetailsServiceImpl(UtilisateurRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Utilisateur user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//       // List<UtilisateurRole> utilisateurRoles=utilisateurRoleRepository.findByUtilisateur(user);
//      //  user.setUtilisateurRoles(utilisateurRoles);
//        return User.withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getUtilisateurRoles().stream()
//                        .map(utilisateurRole ->  utilisateurRole.getRole().getName().name()) // Ajout explicite de ROLE_
//                        .toArray(String[]::new))
//                .build();
//
//
//    }
//}
