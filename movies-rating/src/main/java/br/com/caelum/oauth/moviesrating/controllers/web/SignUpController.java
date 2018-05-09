package br.com.caelum.oauth.moviesrating.controllers.web;

import br.com.caelum.oauth.moviesrating.forms.NewUserForm;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.repositories.Users;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("sign-up")
public class SignUpController {

    private final Users users;

    public SignUpController(Users users) {
        this.users = users;
    }

    @GetMapping
    public ModelAndView form(NewUserForm form){
        ModelAndView view = new ModelAndView("users/form");

        view.addObject("newUserForm", form);

        return view;
    }

    @PostMapping
    public ModelAndView save(@Valid NewUserForm form, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            return form(form);
        }

        User user = form.toUser();

        users.save(user);

        attributes.addFlashAttribute("message", "User " + user.getName() + " was successful saved");

        return new ModelAndView("redirect:/login");
    }

}
