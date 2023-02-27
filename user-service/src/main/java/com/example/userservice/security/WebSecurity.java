package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final AuthenticationConfiguration configuration;
    private final UserService userService;
    private final Environment env;

    @Bean
    protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests().antMatchers("/**")
                //.hasIpAddress("30.20.0.115")
                .permitAll()
                .and()
                .addFilter(authenticationFilter());

        httpSecurity.userDetailsService(userService);

        return httpSecurity.build();
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env);
        authenticationFilter.setAuthenticationManager(configuration.getAuthenticationManager());

        return authenticationFilter;
    }
}
