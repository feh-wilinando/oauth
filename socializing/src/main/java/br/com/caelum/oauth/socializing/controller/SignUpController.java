package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.repositories.Users;
import br.com.caelum.oauth.socializing.forms.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignUpController {

    private Users users;

    public SignUpController(Users users) {
        this.users = users;
    }

    @GetMapping("sign-up")
    public ModelAndView form(SignUpForm form){
        ModelAndView view = new ModelAndView("users/form");


        view.addObject("signUpForm", form);

        return view;
    }
    
    
    @PostMapping("sign-up")
    public ModelAndView save(@Valid SignUpForm form, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()){
            return form(form);
        }

        User newUser = form.toEntity();

        users.save(newUser);

        ModelAndView view = new ModelAndView("redirect:/login");

        redirect.addFlashAttribute("message", "User " + form.getUsername() + " was successful saved.");
        
        return view;
    } 

}
