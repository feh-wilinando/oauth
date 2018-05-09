package br.com.caelum.oauth.moviesrating.controllers.web;

import br.com.caelum.oauth.commons.exceptions.NoContentException;
import br.com.caelum.oauth.commons.exceptions.NotFoundException;
import br.com.caelum.oauth.moviesrating.forms.RatingForm;
import br.com.caelum.oauth.moviesrating.models.Movie;
import br.com.caelum.oauth.moviesrating.models.Rating;
import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.moviesrating.repository.Movies;
import br.com.caelum.oauth.moviesrating.repository.Ratings;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("ratings")
public class RatingController {


    private final Movies movies;
    private Ratings ratings;

    public RatingController(Movies movies, Ratings ratings) {
        this.movies = movies;
        this.ratings = ratings;
    }



    @GetMapping
    public ModelAndView list(@AuthenticationPrincipal ResourceOwner resourceOwner){
        ModelAndView view = new ModelAndView("rating/list");

        User owner = resourceOwner.unwrap();

        view.addObject("ratings", ratings.findAllByOwner(owner, Rating.class));

        return view;
    }

    @GetMapping("new")
    public ModelAndView form(RatingForm form){
        ModelAndView view = new ModelAndView("rating/form");

        if (Objects.nonNull(form.getId())){
            Movie movie = movies.findById(form.getMovieId()).orElseThrow(RuntimeException::new);

            form.setMovieId(movie.getId());

            view.addObject("ratingMovie", movie);

        }

        view.addObject("movies", movies.findAll());
        view.addObject("ratingForm", form);

        return view;
    }
    
    @PostMapping
    public ModelAndView save(@AuthenticationPrincipal ResourceOwner owner, @Valid RatingForm form, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()){
            return form(form);
        }

        Rating rating = form.toEntity(owner.unwrap(), ratings, movies);

        ratings.save(rating);

        ModelAndView view = new ModelAndView("redirect:/ratings");
        
        redirect.addFlashAttribute("message", "Rating was successful saved");

        return view;
    }

    @GetMapping("{id}")
    public ModelAndView edit(@AuthenticationPrincipal ResourceOwner owner, @PathVariable Long id, RatingForm form){
        Optional<Rating> optionalRating = ratings.findByIdAndOwner(id, owner.unwrap());

        optionalRating.ifPresent(form::fillFrom);

        if (optionalRating.isPresent()) {
            return form(form);
        }

        throw new NotFoundException();

    }


    @ResponseBody
    @DeleteMapping("{id}")
    public ResponseEntity<Rating> remove(@AuthenticationPrincipal ResourceOwner owner, @PathVariable Long id){
        Optional<Rating> optionalRating = ratings.findByIdAndOwner(id, owner.unwrap());

        optionalRating.ifPresent(ratings::delete);

        return optionalRating.map(ResponseEntity.accepted()::body).orElseThrow(NoContentException::new);

    }
}
