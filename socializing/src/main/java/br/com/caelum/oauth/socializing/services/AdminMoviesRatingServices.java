package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.socializing.dtos.Movie;
import br.com.caelum.oauth.socializing.dtos.MovieRating;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class AdminMoviesRatingServices {

    public List<MovieRating> getAll(Token token){
        RestTemplate rest = new RestTemplate();

        URI uri = URI.create("http://localhost:8080/api/v2/admin");

        RequestEntity<Void> request = RequestEntity.get(uri)
                                        .header("Authorization", "Bearer " + token.getValue())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .build();


        try {

            ParameterizedTypeReference<List<MovieRating>> typeReference = new ParameterizedTypeReference<>() {};

            ResponseEntity<List<MovieRating>> response = rest.exchange(request, typeReference);


            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }

            return List.of();

        }catch (HttpClientErrorException e){

            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                return List.of();
            }

            throw e;
        }


    }

}
