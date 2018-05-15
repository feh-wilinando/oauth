package br.com.caelum.oauth.moviesrating.repository;

import br.com.caelum.oauth.moviesrating.models.Rating;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.moviesrating.projections.RatingAverage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface Ratings extends Repository<Rating, Long> {
    void save(Rating rating);

    <T> List<T> findAllByOwner(User owner, Class<T> type);

    void delete(Rating rating);

    Optional<Rating> findByIdAndOwner(Long id, User unwrap);

    @Query("select r.movie.title as movie, avg(r.value) as average from Rating r group by r.movie")
    List<RatingAverage> getAllAverage();
}
