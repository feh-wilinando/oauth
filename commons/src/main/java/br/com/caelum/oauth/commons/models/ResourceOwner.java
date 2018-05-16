package br.com.caelum.oauth.commons.models;

import br.com.caelum.oauth.commons.models.vos.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceOwner implements UserDetails {

    private final User user;
    private final Set<GrantedAuthority> computedRoles;

    public ResourceOwner(User user) {
        this.user = user;
        this.computedRoles = user.getRoles()
                                    .stream()
                                        .map(Role::getName)
                                            .map(SimpleGrantedAuthority::new)
                                                .peek(System.out::println)
                                                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return computedRoles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User unwrap() {
        return user;
    }
}
