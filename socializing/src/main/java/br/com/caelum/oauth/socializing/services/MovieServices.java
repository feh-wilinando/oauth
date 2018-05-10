package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.socializing.dtos.Movie;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class MovieServices {


    private RestTemplate rest;

    public MovieServices(RestTemplate rest) {
        this.rest = rest;
    }

    public List<Movie> getAllByUser(Token token){

        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("Authorization", "Bearer " + token.getValue());

        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create("http://localhost:8080/api/v2/ratings"));

        ParameterizedTypeReference<List<Movie>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Movie>> response = rest.exchange(requestEntity, typeReference);


        if (response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }

        throw new UnauthorizedException();
    }
}
