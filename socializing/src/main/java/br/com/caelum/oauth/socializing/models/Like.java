package br.com.caelum.oauth.socializing.models;

import br.com.caelum.oauth.commons.models.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class Like {

    @OneToOne
    @NotNull
    private User user;

    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * @deprecated frameworks only
     */
    @Deprecated(since = "1.0.0")
    Like(){}

    public Like(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(user, like.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user);
    }
}
