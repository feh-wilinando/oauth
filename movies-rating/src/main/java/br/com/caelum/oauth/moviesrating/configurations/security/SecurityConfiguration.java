package br.com.caelum.oauth.moviesrating.configurations.security;

import br.com.caelum.oauth.commons.configurations.security.LoginStrategy;
import br.com.caelum.oauth.commons.configurations.security.SuccessLoginRedirectHandler;
import br.com.caelum.oauth.commons.services.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration {

    private final LoginService loginService;

    public SecurityConfiguration(LoginService loginService) {
        this.loginService = loginService;
    }

    @Configuration
    class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            LoginStrategy loginStrategy = roles -> {
                if (roles.contains("ROLE_ADMIN")) {
                    return "/admin/movies";
                }

                return "/ratings";
            };

            AntPathRequestMatcher logoutMatcher = new AntPathRequestMatcher("/logout", HttpMethod.GET.name());
            AuthenticationSuccessHandler successRedirect = new SuccessLoginRedirectHandler(loginStrategy);

            http
                    .authorizeRequests()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/ratings/**").hasRole("MEMBER")
                        .antMatchers("/sign-up/**").permitAll()
                        .anyRequest().authenticated().and()
                    .formLogin()
//                        .successHandler(successRedirect)
                        .loginPage("/login").permitAll().and()
                    .logout()
                        .logoutRequestMatcher(logoutMatcher).permitAll();
        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/webjars/**");
        }

        @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }
    }


    @Configuration
    @Order(1)
    class ApiSecurityConfiguration extends  WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .anyRequest().authenticated().and()
                    .antMatcher("/api/ratings").httpBasic().and()
                    .csrf().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
        }
    }
}
