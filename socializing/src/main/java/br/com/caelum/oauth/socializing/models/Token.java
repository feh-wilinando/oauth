package br.com.caelum.oauth.socializing.models;

import br.com.caelum.oauth.commons.models.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @CreatedBy
    private User owner;

    private String value;

    /**
     * @deprecated frameworks only
     */
    @Deprecated
    public Token() { }

    public Token(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getValue() {
        return value;
    }
}
