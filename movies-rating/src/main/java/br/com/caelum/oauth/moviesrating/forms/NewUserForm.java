package br.com.caelum.oauth.moviesrating.forms;

import br.com.caelum.oauth.commons.infra.validations.PasswordAndRePasswordMatching;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.models.vos.Credential;
import br.com.caelum.oauth.commons.models.vos.Password;
import br.com.caelum.oauth.commons.models.vos.Role;

import javax.validation.constraints.NotEmpty;


@PasswordAndRePasswordMatching
public class NewUserForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private String repassword;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User toUser(){

        Password password = new Password(this.password);

        Credential credential = new Credential(login, password, Role.MEMBER);

        return new User(name, credential);
    }
}
