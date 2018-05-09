package br.com.caelum.oauth.commons.models.vos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Password {

    private static PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String rawPassword;

    public Password(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public String getEncoded(){
        return encoder.encode(rawPassword);
    }
}
