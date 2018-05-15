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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class MoviesRatingBoot {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRatingBoot.class, args);
    }

    @Autowired
    private Users users;

    @PersistenceContext
    private EntityManager manager;

    @PostConstruct
    public void postConstruct(){

        if (!users.findByLogin("admin").isPresent()) {


            manager.persist(Role.ADMIN);
            manager.persist(Role.MEMBER);

            Password password = new Password("admin");
            Credential credential = new Credential("admin", password, Role.ADMIN);

            User admin = new User("admin", credential);

            users.save(admin);
        }
    }

}
