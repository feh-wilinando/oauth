package br.com.caelum.oauth.moviesrating.forms;

import br.com.caelum.oauth.moviesrating.models.Movie;
import br.com.caelum.oauth.moviesrating.repository.Movies;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class MovieForm {

    @NotEmpty
    private String title;
    private Long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie toEntity(Movies movies) {

        Movie movie = Optional.ofNullable(id)
                        .flatMap(movies::findById)
                            .orElse(new Movie(title));

        movie.updateTitle(this);

        return movie;
    }

    public void fillFrom(Movie movie) {
        this.title = movie.getTitle();
        this.id = movie.getId();
    }
}
