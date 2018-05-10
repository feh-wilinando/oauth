package br.com.caelum.oauth.socializing.configurations;

import br.com.caelum.oauth.commons.configurations.security.SuccessLoginRedirectHandler;
import br.com.caelum.oauth.commons.services.LoginService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private LoginService loginServices;

    public SecurityConfiguration(LoginService loginServices) {
        this.loginServices = loginServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationSuccessHandler successRedirect = new SuccessLoginRedirectHandler(roles -> "/feed");

        http
                .authorizeRequests()
                    .antMatchers("/feel/**").hasRole("MEMBER")
                    .antMatchers("/movies-rating-integration/**").hasRole("MEMBER")
                    .antMatchers("/sign-up/**").permitAll()
                .anyRequest()
                    .authenticated().and()
                .formLogin()
                    .loginPage("/login").permitAll()
                        .successHandler(successRedirect).and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name()));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginServices).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/webjars/**");
    }
}
