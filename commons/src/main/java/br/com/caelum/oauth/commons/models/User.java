package br.com.caelum.oauth.commons.models;

import br.com.caelum.oauth.commons.models.vos.Credential;
import br.com.caelum.oauth.commons.models.vos.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Credential credential;

    /**
     * @deprecated Frameworks only
     */
    @Deprecated(since = "1.0.0")
    User() { }

    public User(String name, Credential credential) {
        this.name = name;
        this.credential = credential;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return credential.getLogin();
    }

    public String getPassword() {
        return credential.getPassword();
    }

    public Set<Role> getRoles() {
        return credential.getRoles();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(credential, user.credential);
    }

    @Override
    public int hashCode() {

        return Objects.hash(credential);
    }
}
