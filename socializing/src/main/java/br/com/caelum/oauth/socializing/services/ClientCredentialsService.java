package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.commons.infra.services.BasicAuthentication;
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
public class ClientCredentialsService {

    public Token getToken(){
        RestTemplate rest = new RestTemplate();


        BasicAuthentication authentication = new BasicAuthentication("admin", "123456");

        URI uri = URI.create("http://localhost:8080/oauth/token");

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("grant_type", "client_credentials");

        RequestEntity<LinkedMultiValueMap<String, String>> request = RequestEntity
                .post(uri)
                .header("Authorization", "Basic " + authentication.getEncoded())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);


        ResponseEntity<OAuthToken> response = rest.exchange(request, OAuthToken.class);


        if (response.getStatusCode().is2xxSuccessful()){
            OAuthToken authToken = response.getBody();

            return new Token(authToken.getAccessToken());
        }

        throw new UnauthorizedException();

    }
}
