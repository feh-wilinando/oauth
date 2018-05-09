package br.com.caelum.oauth.moviesrating.repository;

import br.com.caelum.oauth.moviesrating.models.Movie;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface Movies  extends Repository<Movie, Long> {
    List<Movie> findAll();

    void save(Movie movie);

    Optional<Movie> findById(Long id);

    void delete(Movie movie);
}
