package com.ifmo.web.coursework.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Authenticator implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication.getName() + " " + authentication.getCredentials());
        if ("boris".equals(authentication.getName())) {
            if ("borris".equals(authentication.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_BORIS"),
                                new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        }
        throw new BadCredentialsException("Wrong login/password");
    }


}
