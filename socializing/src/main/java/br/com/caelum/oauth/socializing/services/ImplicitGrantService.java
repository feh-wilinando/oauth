package br.com.caelum.oauth.socializing.services;

import org.apache.catalina.util.URLEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ImplicitGrantService {

    public String getEndpoint(){
        HashMap<String, String> parameters = new HashMap<>();


        parameters.put("response_type", "token");
        parameters.put("client_id", "socializing");
        parameters.put("redirect_uri", "http://localhost:8081/feed");
        parameters.put("scope", "read write");


        return parameters.entrySet().stream()
                .map( entry -> entry.getKey() + "=" + encoded(entry.getValue()))
                .collect(Collectors.joining("&", "http://localhost:8080/oauth/authorize?", ""));

    }


    private String encoded(String value){
        return URLEncoder.DEFAULT.encode(value, Charset.forName("UTF-8"));
    }

}
