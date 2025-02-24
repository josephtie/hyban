//package com.nectux.mizan.hyban.securite;
//
//import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
//import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
//import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
//import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
///*
// Extend AbstractUserDetailsAuthenticationProvider when you want to
// prehandle authentication, as in throwing custom exception messages,
// checking status, etc.
// */
//@Component
//public class LocalAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired private UtilisateurService customerAdvisorService;
//	@Autowired private UtilisateurRoleService customerAdvisorRoleService;
//
//    //private PasswordEncoder encoder;
//	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//	@Override
//	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
//			throws AuthenticationException {
//	}
//
//	@Override
//	public UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken authentication)
//			throws AuthenticationException {
//        String password = (String) authentication.getCredentials();
//        if (!StringUtils.hasText(password)) {
//        	logger.warn("Username {}: no password provided", email);
//            throw new BadCredentialsException("Please enter password");
//        }
//
//        Utilisateur customerAdvisor = customerAdvisorService.findByEmail(email);
//        if (customerAdvisor == null) {
//        	logger.warn("Username {} password {}: user not found", email, password);
//            throw new UsernameNotFoundException("Invalid Login");
//        }
//
//        if (!passwordEncoder.matches(password, customerAdvisor.getMotDePasse())) {
//        	logger.warn("Username {} password {}: invalid password", email, password);
//            throw new BadCredentialsException("Invalid Login");
//        }
//
//        if (!(UserAccountStatus.STATUS_APPROVED.name().equals(customerAdvisor.getStatut()))) {
//        	logger.warn("Username {}: not approved", email);
//            throw new BadCredentialsException("User has not been approved");
//        }
//        if (!customerAdvisor.getActif()) {
//        	logger.warn("Email {}: disabled", email);
//            throw new BadCredentialsException("User disabled");
//        }
//
//        final List<GrantedAuthority> auths;
//        List<UtilisateurRole> customerAdvisorRoles = customerAdvisorRoleService.findByUtilisateur(customerAdvisor);
//        if (!customerAdvisorRoles.isEmpty()) {
//	    	auths = AuthorityUtils.commaSeparatedStringToAuthorityList(customerAdvisorRoleService.getRolesCSV(customerAdvisorRoles));
//        } else {
//        	auths = AuthorityUtils.NO_AUTHORITIES;
//        }
//
//        return new User(email, password, customerAdvisor.getActif(), // enabled
//                true, // account not expired
//                true, // credentials not expired
//                true, // account not locked
//                auths);
//	}
//
//}
