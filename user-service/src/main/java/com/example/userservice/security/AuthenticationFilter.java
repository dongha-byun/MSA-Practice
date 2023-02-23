package com.example.userservice.security;

import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.info("getAuthenticationManager : {}", getAuthenticationManager());
        try {
            RequestLogin credential = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            log.info("credential : {}", credential.toString());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credential.getEmail(),
                    credential.getPassword(),
                    new ArrayList<>());
            log.info("token : {}", token);

            Authentication authenticate = getAuthenticationManager().authenticate(token);
            log.info("authenticate : {}", authenticate);

            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("successful authentication!");
        response.setHeader("TRY_LOGIN_RESULT", "SUCCESS");

        log.info("auth user name : {}", ((User)authResult.getPrincipal()).getUsername());
        //super.successfulAuthentication(request, response, chain, authResult);
    }
}
