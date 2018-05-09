package br.com.caelum.oauth.commons.infra.services;

public class ReflectionPermissionDenied extends RuntimeException {
    public ReflectionPermissionDenied(String message, Exception cause) {
        super(message, cause);
    }
}
