package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.socializing.dtos.Movie;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class MovieServices {

    public List<Movie> getAllByToken(Token token){

        RestTemplate rest = new RestTemplate();

        URI url = URI.create("http://localhost:8080/api/v2/ratings");

        RequestEntity<Void> request = RequestEntity.get(url)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .header("Authorization", "Bearer " + token.getValue())
                                        .build();

        ParameterizedTypeReference<List<Movie>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Movie>> response = rest.exchange(request, typeReference);


        if (response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }

        throw new UnauthorizedException();

    }
}
