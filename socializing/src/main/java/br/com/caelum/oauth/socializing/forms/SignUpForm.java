package br.com.caelum.oauth.socializing.forms;

import br.com.caelum.oauth.commons.infra.validations.PasswordAndRePasswordMatching;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.models.vos.Credential;
import br.com.caelum.oauth.commons.models.vos.Password;
import br.com.caelum.oauth.commons.models.vos.Role;

import javax.validation.constraints.NotEmpty;

@PasswordAndRePasswordMatching
public class SignUpForm {

    @NotEmpty
    private String name;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String repassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public User toEntity() {
        Credential credential = new Credential(username, new Password(password), Role.MEMBER);
        return new User(name, credential);
    }
}
