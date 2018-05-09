package br.com.caelum.oauth.moviesrating.models;

import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.moviesrating.forms.RatingForm;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @Min(0) @Max(5)
    private Integer value;

    private String comment;

    @CreatedBy
    @ManyToOne
    private User owner;

    /**
     * @deprecated frameworks only
     */
    @Deprecated(since = "1.0.0")
    Rating(){}

    public Rating(Movie movie, Integer value, String comment) {
        this.movie = movie;
        this.value = value;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Integer getValue() {
        return value;
    }

    public User getOwner() {
        return owner;
    }

    public String getComment() {
        return comment;
    }

    public void updateCommentAndRating(RatingForm form) {
        comment = form.getComment();
        value = form.getValue();
    }
}
