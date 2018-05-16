package br.com.caelum.oauth.moviesrating.configurations.security;

import br.com.caelum.oauth.commons.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

@Configuration
public class OAuthConfiguration {


    private static final String RESOURCE_ID = "movies";


    @Configuration
    @EnableResourceServer
    class ResourceServer extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .anyRequest().authenticated().and()
                    .requestMatchers()
                        .antMatchers("/api/v2/**");
        }
    }

    @Configuration
    @EnableAuthorizationServer
    class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager manager;

        @Autowired
        private LoginService loginService;

        @Autowired
        private ClientDetailsService clientDetails;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                        .withClient("socializing")
                        .secret("{noop}123456")
                        .authorizedGrantTypes("authorization_code", "refresh_token")
                        .scopes("read", "write")
                        .authorities("read", "write")
                        .accessTokenValiditySeconds(120)
                        .resourceIds(RESOURCE_ID);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

            DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetails);

            requestFactory.setCheckUserScopes(true);

            endpoints
                    .authenticationManager(manager)
                    .userDetailsService(loginService)
                    .requestFactory(requestFactory);
        }
    }
}
