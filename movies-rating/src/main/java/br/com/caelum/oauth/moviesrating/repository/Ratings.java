package br.com.caelum.oauth.moviesrating.repository;

import br.com.caelum.oauth.moviesrating.models.Rating;
import br.com.caelum.oauth.commons.models.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface Ratings extends Repository<Rating, Long> {
    void save(Rating rating);

    <T> List<T> findAllByOwner(User owner, Class<T> type);

    void delete(Rating rating);

    Optional<Rating> findByIdAndOwner(Long id, User unwrap);
}
