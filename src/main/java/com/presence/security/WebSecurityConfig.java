package com.presence.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //pentru parsarea parolelor in baza de date
    //avem nevoie sa le criptam mai intai
    //Java Spring lucreaza foarte bine cu BCrypt
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //folosim managerul inclus in Java Spirng Securiry pentru
    //a configura securitatea aplicatiei noastre
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager()).passwordEncoder(passwordEncoder());
    }

    //returnam un obiect gol care contine managerul userilor
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        return new InMemoryUserDetailsManager(users);
    }

    //vom configura permisiunile la accesarea paginilor web
    //descrise in aplicatie
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/h2").permitAll()
                .antMatchers("/", "/").hasAuthority("USER")
                .antMatchers("/", "/account").hasAuthority("USER")
                .antMatchers("/", "/absence").hasAuthority("USER")
                .antMatchers("/", "/admin_user").hasAuthority("USER")
                .antMatchers("/", "/companies").hasAuthority("USER")
                .antMatchers("/", "/rest_get_paid").hasAuthority("USER")
                .antMatchers("/", "/rest_not_paid").hasAuthority("USER")
                .antMatchers("/", "/store_rest").hasAuthority("USER")
                .antMatchers("/", "/storerestnopay").hasAuthority("USER")
                .antMatchers("/", "/teams").hasAuthority("USER")
                .antMatchers("/", "/upload").hasAuthority("USER")
                .and().formLogin().loginPage("/login").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}