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

import javax.transaction.Transactional;
import java.net.URI;

@Service
public class RefreshTokenService {



    @Transactional
    public void refresh(Token token){

        RestTemplate rest = new RestTemplate();

        URI uri = URI.create("http://localhost:8080/oauth/token");


        BasicAuthentication basicAuthentication = new BasicAuthentication("socializing", "123456");

        LinkedMultiValueMap<String, String> payload = new LinkedMultiValueMap<>();

        payload.add("grant_type", "refresh_token");
        payload.add("refresh_token", token.getRefreshToken());
        payload.add("scopes", "read write");

        RequestEntity<LinkedMultiValueMap<String, String>> request = RequestEntity.post(uri)
                .header("Authorization", "Basic " + basicAuthentication.getEncoded())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(payload);


        ResponseEntity<OAuthToken> response = rest.exchange(request, OAuthToken.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            OAuthToken authToken = response.getBody();

            token.updateFromOAuthToken(authToken);

            return;
        }

        throw new UnauthorizedException();

    }

}
