package br.com.caelum.oauth.socializing.models;

import br.com.caelum.oauth.commons.models.User;
import br.com.caelum.oauth.socializing.dtos.OAuthToken;
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

    private String accessToken;

    private String refreshToken;


    /**
     * @deprecated frameworks only
     */
    @Deprecated(since = "1.0.0")
    public Token() { }

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getAccessToken() {
        return accessToken;
    }


    public void updateFromOAuthToken(OAuthToken token){
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}
