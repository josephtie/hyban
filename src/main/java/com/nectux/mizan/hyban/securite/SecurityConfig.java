package com.nectux.mizan.hyban.securite;



import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/hyban/api/auth/**", "/hyban/", "/hyban/home").permitAll()
                        .antMatchers("/hyban/resources/**", "/hyban/static/**", "/hyban/js/**", "/hyban/css/**", "/hyban/images/**", "/hyban/img/**").permitAll()
                        .antMatchers("/login", "/register", "/hyban/static/**", "/hyban/logo/**", "/hyban/js/**", "/hyban/images/**", "/hyban/WEB-INF/**").permitAll()
                        .antMatchers("/views/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Mise à jour avec le préfixe /hyban
                        .loginProcessingUrl("/hyban/j_spring_security_check")  // URL de traitement du login
                        .usernameParameter("j_username")
                        .passwordParameter("j_password")
                        .defaultSuccessUrl("/welcome", true)  // Redirection après succès
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Mise à jour avec /hyban
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }



//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/resources/**", "/static/**", "/js/**", "/css/**", "/images/**", "/img/**");
//    }

        @Bean
        public UserDetailsService userDetailsService(UserDetailsServiceImpl userDetailsServiceImpl) {
            return userDetailsServiceImpl;
        }

        @Bean
        public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder);
            return authProvider;
        }



        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(); // 🔥 Toujours encoder les mots de passe !
        }
    //}



}


