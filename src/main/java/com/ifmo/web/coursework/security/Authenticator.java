package com.ifmo.web.coursework.security;

import com.ifmo.web.coursework.entity.Human;
import com.ifmo.web.coursework.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class Authenticator implements AuthenticationManager {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    HumanRepository humanRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
        if (null == authentication.getName() || null == authentication.getCredentials()) {
            throw new BadCredentialsException("No login or password provided!");
        }
        Optional<Human> byLogin = humanRepository.findByLogin(authentication.getName());
        Human human = byLogin.orElseThrow(() -> new BadCredentialsException("Wrong login"));

        if (!human.getPassword().equals(encoder.encode(authentication.getCredentials().toString()))) {
            throw new BadCredentialsException("Wrong login/password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(),
                authentication.getCredentials(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_BORIS"),
                        new SimpleGrantedAuthority("ROLE_USER"))); // FIXME: 05.04.19
    }


}
