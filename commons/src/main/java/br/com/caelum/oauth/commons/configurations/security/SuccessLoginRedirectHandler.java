package br.com.caelum.oauth.commons.configurations.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SuccessLoginRedirectHandler implements AuthenticationSuccessHandler {


    private final LoginStrategy loginStrategy;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public SuccessLoginRedirectHandler(LoginStrategy loginStrategy) {
        this.loginStrategy = loginStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String target = determineTargetBy(authentication);

        redirectStrategy.sendRedirect(request, response, target);

    }

    private String determineTargetBy(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        return loginStrategy.getPageByRoles(authorities);
    }
}
