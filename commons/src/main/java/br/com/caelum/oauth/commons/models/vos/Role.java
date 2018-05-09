package br.com.caelum.oauth.commons.models.vos;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Role {

    public static final Role MEMBER = new Role("ROLE_MEMBER");
    public static final Role ADMIN = new Role("ROLE_ADMIN");

    @NotEmpty
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
