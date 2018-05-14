package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.socializing.repositories.Tokens;
import br.com.caelum.oauth.socializing.services.ImplicitGrantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("movies-rating-integration")
public class MoviesRatingIntegrationController {


    private Tokens tokens;
    private ImplicitGrantService implicitGrantService;

    public MoviesRatingIntegrationController(Tokens tokens, ImplicitGrantService implicitGrantService) {
        this.tokens = tokens;
        this.implicitGrantService = implicitGrantService;
    }



    @GetMapping
    public String authentication(){
        return implicitGrantService.getEndpoint();
    }

}
