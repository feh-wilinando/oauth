package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.socializing.repositories.Tokens;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("movies-rating-integration")
public class MoviesRatingIntegrationController {


    private Tokens tokens;

    public MoviesRatingIntegrationController(Tokens tokens) {
        this.tokens = tokens;
    }



}
