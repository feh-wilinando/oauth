package br.com.caelum.oauth.moviesrating.controllers.rest;

import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.moviesrating.projections.MovieRating;
import br.com.caelum.oauth.moviesrating.repository.Ratings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"api", "/api/v2"})
public class RatingRestController {

    private final Ratings ratings;

    public RatingRestController(Ratings ratings) {
        this.ratings = ratings;
    }

    @GetMapping("ratings")
    @PreAuthorize(value = "#oauth2.hasScope('read') and hasRole('MEMBER')")
    public List<MovieRating> list(@AuthenticationPrincipal ResourceOwner owner){
        return ratings.findAllByOwner(owner.unwrap(), MovieRating.class);
    }

}
