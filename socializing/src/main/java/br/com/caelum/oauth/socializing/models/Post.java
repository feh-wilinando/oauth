package br.com.caelum.oauth.socializing.models;

import br.com.caelum.oauth.commons.models.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    @ManyToOne
    private User owner;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<>();

    /**
     * @deprecated frameworks only
     */
    @Deprecated(since = "1.0.0")
    Post(){}

    public Post(String content) {
        this.content = content;
    }


    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getOwner() {
        return owner;
    }

    public boolean addLike(Like like){
        return likes.add(like);
    }

    public boolean isLikedBy(User user){
        return likes.stream().map(Like::getUser).anyMatch(user::equals);
    }

    public int getTotalLikes(){
        return likes.size();
    }
}
