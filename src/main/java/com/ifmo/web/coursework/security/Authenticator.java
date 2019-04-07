package com.ifmo.web.coursework.security;

import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class Authenticator implements AuthenticationManager {
    private final PasswordEncoder encoder;
    private final HumanRepository humanRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
        if (null == authentication.getName() || null == authentication.getCredentials()) {
            throw new BadCredentialsException("No username or password provided!");
        }
        Optional<Human> byLogin = humanRepository.findByLogin(authentication.getName());
        Human human = byLogin.orElseThrow(() -> new BadCredentialsException("Wrong username"));

        if (!encoder.matches(authentication.getCredentials().toString(), human.getPassword())) {
            throw new BadCredentialsException("Wrong username/password");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER_" + human.getHumanId()));

        if (human.getModerator()) authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        if (human.getArchaeologist()) authorities.add(new SimpleGrantedAuthority("ROLE_ARCHAEOLOGIST"));
        if (human.getResearcher()) authorities.add(new SimpleGrantedAuthority("ROLE_RESEARCHER"));
        if (human.getCollector()) authorities.add(new SimpleGrantedAuthority("ROLE_COLLECTOR"));
        if (human.getSponsor()) authorities.add(new SimpleGrantedAuthority("ROLE_SPONSOR"));

        return new UsernamePasswordAuthenticationToken(authentication.getName(),
                authentication.getCredentials(),
                authorities);
    }

    @Autowired
    public Authenticator(PasswordEncoder encoder, HumanRepository humanRepository) {
        this.encoder = encoder;
        this.humanRepository = humanRepository;
    }
}
