package br.com.caelum.oauth.socializing.repositories;

import br.com.caelum.oauth.socializing.models.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface Posts extends Repository<Post, Long> {
    void save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll(Sort sort);
}
