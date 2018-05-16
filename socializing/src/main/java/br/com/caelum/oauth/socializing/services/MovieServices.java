package br.com.caelum.oauth.socializing.services;

import br.com.caelum.oauth.commons.exceptions.UnauthorizedException;
import br.com.caelum.oauth.socializing.dtos.Movie;
import br.com.caelum.oauth.socializing.models.Token;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServices {

    private final RefreshTokenService refreshTokenService;
    private UnauthorizedException lastExceptionThrown;

    public MovieServices(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    public List<Movie> getAllByToken(Token token) {

        Optional<ResponseEntity<List<Movie>>> optionalListResponse = request(token);


        ResponseEntity<List<Movie>> response = optionalListResponse.orElseGet(() -> refreshToken(token));


        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new UnauthorizedException();


    }

    private ResponseEntity<List<Movie>> refreshToken(Token token) {
        refreshTokenService.refresh(token);

        return request(token).orElseThrow(() -> lastExceptionThrown);
    }

    private Optional<ResponseEntity<List<Movie>>> request(Token token) {

        RestTemplate rest = new RestTemplate();

        URI url = URI.create("http://localhost:8080/api/v2/ratings");

        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .build();

        ParameterizedTypeReference<List<Movie>> returnType = new ParameterizedTypeReference<>() {};

        try {
            ResponseEntity<List<Movie>> response = rest.exchange(request, returnType);

            return Optional.of(response);

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            System.err.println(e.getStatusCode());
            System.err.println(e.getResponseHeaders());
            System.err.println(e.getResponseBodyAsString());
            System.err.println(e.getMessage());

            lastExceptionThrown = new UnauthorizedException(e.getResponseBodyAsString());

            return Optional.empty();
        }
    }
}
