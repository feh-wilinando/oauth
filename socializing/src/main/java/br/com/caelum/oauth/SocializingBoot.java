package br.com.caelum.oauth;

import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.models.vos.Credential;
import br.com.caelum.oauth.commons.models.vos.Password;
import br.com.caelum.oauth.commons.models.vos.Role;
import br.com.caelum.oauth.commons.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SocializingBoot {
    public static void main(String[] args) {
        SpringApplication.run(SocializingBoot.class, args);
    }


    @Autowired
    private Users users;

    @PostConstruct
    public void postConstruct(){

        if (!users.findByLogin("admin").isPresent()) {

            Password password = new Password("admin");
            Credential credential = new Credential("admin", password, Role.ADMIN);

            User admin = new User("admin", credential);

            users.save(admin);
        }
    }
}
