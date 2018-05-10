package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.socializing.dtos.OAuthToken;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class TokenServices {

    private RestTemplate rest;

    public TokenServices(RestTemplate rest) {
        this.rest = rest;
    }


    public Token getTokenBy(String username, String rawpassword){

        URI uri = URI.create("http://localhost:8080/oauth/token");

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", rawpassword);
        body.add("scope", "read write");


        BasicAuthentication clientAuthentication = new BasicAuthentication("socializing", "123456");


        RequestEntity<Object> request = RequestEntity
                                            .post(uri)
                                                .header("Authorization", "Basic " + clientAuthentication.getEncoded())
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            .body(body);


        ResponseEntity<OAuthToken> response = rest.exchange(request, OAuthToken.class);

        if (response.getStatusCode().is2xxSuccessful()) {

            OAuthToken oAuthToken= response.getBody();

            return new Token(oAuthToken.getAccessToken());
        }

        throw new UnauthorizedException("Fail to retrive oauth token");

    }

}
