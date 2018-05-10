package br.com.caelum.oauth.socializing.services;

import java.util.Base64;

public class BasicAuthentication {


    private final String credential;

    public BasicAuthentication(String username, String password) {
        this.credential = username + ":" + password;
    }

    public String getEncoded(){
        return Base64.getEncoder().encodeToString(credential.getBytes());
    }
}
