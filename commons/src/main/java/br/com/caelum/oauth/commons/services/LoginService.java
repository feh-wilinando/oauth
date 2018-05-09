package br.com.caelum.oauth.commons.services;

import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.commons.repositories.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final Users users;

    public LoginService(Users users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        return users
                .findByLogin(login)
                    .map(ResourceOwner::new)
                        .orElseThrow(() -> new UsernameNotFoundException(login + " not found"));
    }
}
