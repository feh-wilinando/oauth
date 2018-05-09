package br.com.caelum.oauth.moviesrating.controllers.web;

import br.com.caelum.oauth.commons.exceptions.NoContentException;
import br.com.caelum.oauth.commons.exceptions.NotFoundException;
import br.com.caelum.oauth.moviesrating.forms.MovieForm;
import br.com.caelum.oauth.moviesrating.models.Movie;
import br.com.caelum.oauth.moviesrating.repository.Movies;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("admin/movies")
public class AdminMoviesController {

    private final Movies movies;

    public AdminMoviesController(Movies movies){
        this.movies = movies;
    }

    @GetMapping
    public ModelAndView list(){
        ModelAndView view = new ModelAndView("admin/movies/list");

        view.addObject("movies", movies.findAll());

        return view;
    }


    @GetMapping("new")
    public ModelAndView form(MovieForm form){
        ModelAndView view = new ModelAndView("admin/movies/form");

        view.addObject("movieForm", form);

        return view;
    }

    @GetMapping("{id}")
    public ModelAndView edit(@PathVariable Long id, MovieForm form){
        Optional<Movie> optionalMovie = movies.findById(id);

        optionalMovie.ifPresent(form::fillFrom);

        if (optionalMovie.isPresent()){
            return form(form);
        }

        throw new NotFoundException();

    }

    @PostMapping
    @Transactional
    public ModelAndView save(@Valid MovieForm form, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            return form(form);
        }

        Movie movie = form.toEntity(movies);

        movies.save(movie);

        attributes.addFlashAttribute("message", "Movie was successful saved");

        return new ModelAndView("redirect:/admin/movies");
    }


    @ResponseBody
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> remove(@PathVariable Long id){

        Optional<Movie> optionalMovie = movies.findById(id);

        optionalMovie.ifPresent(movies::delete);


        return optionalMovie.map(ResponseEntity.accepted()::body).orElseThrow(NoContentException::new);

    }

}
