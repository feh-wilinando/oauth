package br.com.caelum.oauth.commons.models.vos;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class Role implements Serializable {

    public static final Role MEMBER = new Role("ROLE_MEMBER");
    public static final Role ADMIN = new Role("ROLE_ADMIN");
    public static final Role READ = new Role("read");

    @Id
    private String name;

    /**
     * @deprecated Frameworks only
     */
    @Deprecated(since = "1.0.0")
    Role() { }

    public Role(String name) {
        Assert.hasText(name, "Name required");
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
