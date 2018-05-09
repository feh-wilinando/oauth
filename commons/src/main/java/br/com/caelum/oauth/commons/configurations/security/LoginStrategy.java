package br.com.caelum.oauth.commons.configurations.security;

import java.util.Set;

@FunctionalInterface
public interface LoginStrategy {

    String getPageByRoles(Set<String> roles);

}
