package br.com.caelum.oauth.socializing.controller;

import br.com.caelum.oauth.commons.exceptions.AlreadyGoneException;
import br.com.caelum.oauth.commons.exceptions.NoContentException;
import br.com.caelum.oauth.commons.exceptions.NotFoundException;
import br.com.caelum.oauth.commons.models.ResourceOwner;
import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.socializing.forms.PostForm;
import br.com.caelum.oauth.socializing.models.Like;
import br.com.caelum.oauth.socializing.models.Post;
import br.com.caelum.oauth.socializing.repositories.Posts;
import org.hibernate.mapping.Collection;
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

    public FeedController(Posts posts) {
        this.posts = posts;
    }

    @GetMapping
    public ModelAndView list(@AuthenticationPrincipal ResourceOwner owner, PostForm form){
        ModelAndView view = new ModelAndView("feed/list");

        view.addObject("postForm", form);
        view.addObject("posts", posts.findAll(Sort.by(Sort.Direction.DESC, "createdDate")));
        view.addObject("currentUser", owner.unwrap());
        view.addObject("movies", List.of());

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
