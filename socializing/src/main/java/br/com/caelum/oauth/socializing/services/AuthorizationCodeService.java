package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.commons.infra.services.BasicAuthentication;
import br.com.caelum.oauth.commons.repositories.Users;
import br.com.caelum.oauth.socializing.dtos.OAuthToken;
import br.com.caelum.oauth.socializing.models.Token;
import org.apache.catalina.util.URLEncoder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class AuthorizationCodeService {

    private Users users;

    public AuthorizationCodeService(Users users) {
        this.users = users;
    }

    public String getAuthorizationURL() {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("client_id", "socializing");
        parameters.put("response_type", "code");
        parameters.put("redirect_uri", "http://localhost:8081/movies-rating-integration/callback");
        parameters.put("scope", "read write");

        return parameters.entrySet()
                    .stream().map(entry -> entry.getKey() + "=" + urlEncoded(entry.getValue()))
                    .collect(Collectors.joining("&","http://localhost:8080/oauth/authorize?", ""));

    }

    private String urlEncoded(String raw){
        return URLEncoder.QUERY.encode(raw, Charset.forName("UTF-8"));
    }


    public Token getToken(String code){
        RestTemplate rest = new RestTemplate();

        BasicAuthentication clientCredential = new BasicAuthentication("socializing", "123456");

        URI uri = URI.create("http://localhost:8080/oauth/token");


        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", "http://localhost:8081/movies-rating-integration/callback");
        body.add("scope", "read write");
        body.add("code", code);

        RequestEntity<Object> request = RequestEntity
                                            .post(uri)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .header("Authorization", "Basic " + clientCredential.getEncoded())
                                                .body(body);

        ResponseEntity<OAuthToken> response = rest.exchange(request, OAuthToken.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            OAuthToken token = response.getBody();

            return new Token(token.getAccessToken(), token.getRefreshToken());
        }

        throw new UnauthorizedException("Fail to retrieve oauth token");

    }
}
