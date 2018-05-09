package br.com.caelum.oauth.moviesrating.projections;

import org.springframework.beans.factory.annotation.Value;

public interface MovieRating {

    @Value("#{target.movie.title}")
    String getMovie();

    @Value("#{target.value}")
    Integer getRating();

    String getComment();
}
