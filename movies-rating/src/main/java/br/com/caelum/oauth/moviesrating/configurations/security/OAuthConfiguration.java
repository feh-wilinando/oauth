package br.com.caelum.oauth.moviesrating.configurations.security;

import br.com.caelum.oauth.commons.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

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
        private LoginService loginService;

        @Autowired
        private ClientDetailsService clientDetails;

        @Autowired
        private DataSource dataSource;

        @Bean
        public TokenStore tokenStore(){
            return new JdbcTokenStore(dataSource);
        }

        @Bean
        public ApprovalStore approvalStore(){
            return new JdbcApprovalStore(dataSource);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.jdbc(dataSource);

        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

            DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetails);

            requestFactory.setCheckUserScopes(true);

            endpoints
                    .userDetailsService(loginService)
                    .requestFactory(requestFactory)
                    .approvalStore(approvalStore())
                    .tokenStore(tokenStore());
        }
    }
}
