package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.socializing.dtos.MovieRating;
import br.com.caelum.oauth.socializing.models.Token;
import br.com.caelum.oauth.socializing.services.AdminMoviesRatingServices;
import br.com.caelum.oauth.socializing.services.ClientCredentialsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminPostController {

    private final AdminMoviesRatingServices moviesRating;
    private final ClientCredentialsService credentialsService;

    public AdminPostController(AdminMoviesRatingServices moviesRating, ClientCredentialsService credentialsService) {
        this.moviesRating = moviesRating;
        this.credentialsService = credentialsService;
    }


    @GetMapping("average")
    public List<MovieRating> getAverage(){

        Token token = credentialsService.getToken();

        return moviesRating.getAll(token);

    }
}
