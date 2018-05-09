package br.com.caelum.oauth.commons.repositories;

import br.com.caelum.oauth.commons.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Users extends Repository<User, Long> {

    @Query("select u from User u where u.credential.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    void save(User user);
}
