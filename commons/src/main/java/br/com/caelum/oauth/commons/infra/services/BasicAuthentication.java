package br.com.caelum.oauth.commons.infra.services;

import org.springframework.util.Base64Utils;

public class BasicAuthentication {


    private final String credential;

    public BasicAuthentication(String username, String password) {
        this.credential = username + ":" + password;
    }

    public String getEncoded(){
        return Base64Utils.encodeToString(credential.getBytes());
    }
}
