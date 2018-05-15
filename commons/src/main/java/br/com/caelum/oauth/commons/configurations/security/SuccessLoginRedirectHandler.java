package br.com.caelum.oauth.commons.configurations.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class SuccessLoginRedirectHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    private final LoginStrategy loginStrategy;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private RequestCache requestCache = new HttpSessionRequestCache();

    public SuccessLoginRedirectHandler(LoginStrategy loginStrategy) {
        this.loginStrategy = loginStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Optional<SavedRequest> savedRequest = Optional.ofNullable(requestCache.getRequest(request, response));


        Boolean authorizedRequest = savedRequest.map(SavedRequest::getRedirectUrl).map(s -> s.contains("/oauth/authorize")).orElse(false);


        if (authorizedRequest) {
            super.onAuthenticationSuccess(request,response,authentication);
            return;
        }

        String target = determineTargetBy(authentication);

        redirectStrategy.sendRedirect(request, response, target);

    }

    private String determineTargetBy(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());


        return loginStrategy.getPageByRoles(authorities);
    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        super.setRequestCache(requestCache);
        this.requestCache = requestCache;
    }
}
