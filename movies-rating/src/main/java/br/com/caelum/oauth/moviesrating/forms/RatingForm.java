package br.com.caelum.oauth.moviesrating.forms;

import br.com.caelum.oauth.commons.exceptions.NotFoundException;
import br.com.caelum.oauth.moviesrating.models.Movie;
import br.com.caelum.oauth.moviesrating.models.Rating;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.moviesrating.repository.Movies;
import br.com.caelum.oauth.moviesrating.repository.Ratings;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class RatingForm {

    private Long id;

    @NotNull
    private Long movieId;

    @NotNull
    @Min(0) @Max(5)
    private Integer value;

    private String comment;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating toEntity(User owner, Ratings ratings, Movies movies) {

        Movie movie = movies.findById(movieId)
                                .orElseThrow(NotFoundException::new);

        Rating rating = Optional.ofNullable(id)
                            .flatMap(ratingId -> ratings.findByIdAndOwner(ratingId, owner))
                                .orElseGet(() -> new Rating(movie, value, comment));

        rating.updateCommentAndRating(this);

        return rating;
    }

    public void fillFrom(Rating rating) {
        id = rating.getId();
        value = rating.getValue();
        movieId = rating.getMovie().getId();
        comment = rating.getComment();
    }
}
