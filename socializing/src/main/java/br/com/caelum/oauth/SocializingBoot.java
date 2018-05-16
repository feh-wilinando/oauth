package br.com.caelum.oauth;

import br.com.caelum.oauth.commons.models.vos.Role;
import br.com.caelum.oauth.commons.repositories.Roles;
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
    private Roles roles;

    @PostConstruct
    public void load() {

        roles.save(Role.MEMBER);
        roles.save(Role.ADMIN);
    }
}
