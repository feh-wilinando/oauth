package br.com.caelum.oauth.moviesrating.models;

import br.com.caelum.oauth.moviesrating.forms.MovieForm;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    /**
     * @deprecated Frameworks only
     */
    @Deprecated(since = "1.0.0")
    Movie() {}


    public Movie(String title) {
        Assert.hasText(title, "Title required");

        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void updateTitle(MovieForm form) {
        this.title = form.getTitle();
    }
}
