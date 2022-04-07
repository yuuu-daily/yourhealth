package com.example.yourhealth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.yourhealth.filter.FormAuthenticationProvider;
import com.example.yourhealth.repository.UserRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    UserDetailsService service;

    @Autowired
    private FormAuthenticationProvider authenticationProvider;

    private static final String[] URLS = { "/css/**", "/images/**", "/scripts/**", "/h2-console/**" };

    /**
    * 認証から除外する
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(URLS);
    }

    /**
    * 認証を設定する
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests().antMatchers("/login", "/logout-complete", "/users/new", "/user").permitAll()
                .anyRequest().authenticated()
                // ログアウト処理
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout-complete").clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).permitAll().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // form
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/topics").failureUrl("/login-failure")
                .permitAll();
        // @formatter:on
    }
}