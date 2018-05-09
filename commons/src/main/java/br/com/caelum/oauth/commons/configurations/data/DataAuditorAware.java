package br.com.caelum.oauth.commons.configurations.data;

import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.commons.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DataAuditorAware {


    @Bean
    public AuditorAware<User> auditorAware(){
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.isAuthenticated()) {
                ResourceOwner resourceOwner = (ResourceOwner) authentication.getPrincipal();

                User user = resourceOwner.unwrap();

                return Optional.ofNullable(user);
            }

            throw new InsufficientAuthenticationException("User doesn't authenticated");
        };
    }

}
