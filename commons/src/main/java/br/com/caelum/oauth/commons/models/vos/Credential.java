package br.com.caelum.oauth.commons.models.vos;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class Credential {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    /**
     * @deprecated Frameworks only
     */
    @Deprecated(since = "1.0.0")
    Credential() { }

    public Credential(String login, Password password, Role... roles) {
        Assert.hasText(login, "Login required");
        Assert.notNull(password, "Password required");

        this.login = login;
        this.password = password.getEncoded();
        this.roles =  Set.of(roles);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login);
    }
}
