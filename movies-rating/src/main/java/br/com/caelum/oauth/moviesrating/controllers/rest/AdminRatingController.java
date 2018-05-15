package br.com.caelum.oauth.moviesrating.controllers.rest;

import br.com.caelum.oauth.moviesrating.projections.RatingAverage;
import br.com.caelum.oauth.moviesrating.repository.Ratings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v2/admin")
public class AdminRatingController {


    private Ratings ratings;

    public AdminRatingController(Ratings ratings) {
        this.ratings = ratings;
    }

    @GetMapping
    public List<RatingAverage> getAverage(){
        return ratings.getAllAverage();
    }

}
