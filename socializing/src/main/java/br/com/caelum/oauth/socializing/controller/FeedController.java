package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.commons.exceptions.AlreadyGoneException;
import br.com.caelum.oauth.commons.exceptions.NoContentException;
import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.commons.repositories.Users;
import br.com.caelum.oauth.socializing.dtos.Movie;
import br.com.caelum.oauth.socializing.forms.PostForm;
import br.com.caelum.oauth.socializing.models.Like;
import br.com.caelum.oauth.socializing.models.Post;
import br.com.caelum.oauth.socializing.repositories.Posts;
import br.com.caelum.oauth.socializing.repositories.Tokens;
import br.com.caelum.oauth.socializing.services.MovieServices;
import br.com.caelum.oauth.socializing.services.TokenServices;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("feed")
public class FeedController {

    private Posts posts;
    private Tokens tokens;
    private MovieServices movieServices;

    public FeedController(Posts posts, Tokens tokens, MovieServices movieServices) {
        this.posts = posts;
        this.tokens = tokens;
        this.movieServices = movieServices;
    }

    @GetMapping
    public ModelAndView list(@AuthenticationPrincipal ResourceOwner resourceOwner, PostForm form){
        ModelAndView view = new ModelAndView("feed/list");

        User owner = resourceOwner.unwrap();

        view.addObject("postForm", form);
        view.addObject("posts", posts.findAll(Sort.by(Sort.Direction.DESC, "createdDate")));
        view.addObject("currentUser", owner);

        List<Movie> movies = tokens.findByOwner(owner)
                                    .map(movieServices::getAllByUser).orElse(List.of());

        view.addObject("movies", movies);

        return view;
    }
    
    
    @PostMapping
    public ModelAndView save(@AuthenticationPrincipal ResourceOwner owner, @Valid PostForm form, BindingResult result){

        if(result.hasErrors()){
            return list(owner, form);
        }

        Post post = form.toEntity();

        posts.save(post);

        return new ModelAndView("redirect:/feed");
    }


    @PutMapping
    public ModelAndView saveMoviePost(@AuthenticationPrincipal ResourceOwner owner, @RequestBody Movie movie){
        Post post = movie.toPost();

        posts.save(post);

        ModelAndView view = new ModelAndView("components :: post");

        view.addObject("post", post);
        view.addObject("currentUser", owner.unwrap());

        return view;
    }

    @Transactional
    @PutMapping("{id}")
    public ModelAndView like(@AuthenticationPrincipal ResourceOwner owner, @PathVariable Long id){

        Post post = posts.findById(id).orElseThrow(NoContentException::new);

        ModelAndView view = new ModelAndView("components :: post");

        User currentUser = owner.unwrap();
        Like like = new Like(currentUser);

        if (!post.addLike(like)) {
            throw new AlreadyGoneException();
        }

        view.addObject("post", post);
        view.addObject("currentUser", currentUser);

        return view;
    }
}
