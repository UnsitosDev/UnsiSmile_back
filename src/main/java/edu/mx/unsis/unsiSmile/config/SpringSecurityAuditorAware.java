package edu.mx.unsis.unsiSmile.config;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserModel user) {
                return Optional.ofNullable(user.getId().toString());
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}