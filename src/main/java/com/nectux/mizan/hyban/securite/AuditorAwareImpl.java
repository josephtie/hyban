package com.nectux.mizan.hyban.securite;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            return Optional.of(((UserDetails) principal).getUsername());
        }
        return Optional.of("SYSTEM"); // fallback en cas d'appel non authentifié
    }
}
