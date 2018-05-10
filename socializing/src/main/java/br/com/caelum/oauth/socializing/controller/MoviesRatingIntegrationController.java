package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.socializing.forms.LoginForm;
import br.com.caelum.oauth.socializing.models.Token;
import br.com.caelum.oauth.socializing.repositories.Tokens;
import br.com.caelum.oauth.socializing.services.TokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("movies-rating-integration")
public class MoviesRatingIntegrationController {


    private TokenServices tokenServices;
    private Tokens tokens;

    public MoviesRatingIntegrationController(TokenServices tokenServices, Tokens tokens) {
        this.tokenServices = tokenServices;
        this.tokens = tokens;
    }

    @GetMapping
    public ModelAndView form(LoginForm form){
        ModelAndView view = new ModelAndView("integrations/form");

        view.addObject("loginForm", form);

        return view;
    }
    
    
    @PostMapping
    public ModelAndView save(@Valid LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return form(form);
        }

        Token token = tokenServices.getTokenBy(form.getUsername(), form.getPassword());

        tokens.save(token);

        return new ModelAndView("redirect:/feed");
    }


}
