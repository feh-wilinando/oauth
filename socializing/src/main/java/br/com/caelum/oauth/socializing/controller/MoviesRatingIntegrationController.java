package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.socializing.models.Token;
import br.com.caelum.oauth.socializing.repositories.Tokens;
import br.com.caelum.oauth.socializing.services.AuthorizationCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("movies-rating-integration")
public class MoviesRatingIntegrationController {


    private AuthorizationCodeService authorizationCodeService;
    private Tokens tokens;

    public MoviesRatingIntegrationController(AuthorizationCodeService authorizationCodeService, Tokens tokens) {
        this.authorizationCodeService = authorizationCodeService;
        this.tokens = tokens;
    }


    @GetMapping
    public String authentication(){
        return "redirect:" + authorizationCodeService.getAuthorizationURL();
    }


    @GetMapping("callback")
    public String callback(@RequestParam("code") String code){

        Token token = authorizationCodeService.getToken(code);

        tokens.save(token);

        return "redirect:/feed";
    }

}
