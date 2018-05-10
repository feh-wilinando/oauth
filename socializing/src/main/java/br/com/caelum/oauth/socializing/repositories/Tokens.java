package br.com.caelum.oauth.socializing.repositories;

import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface Tokens extends Repository<Token, Long> {
    Optional<Token> findByOwner(User owner);

    void save(Token token);
}
