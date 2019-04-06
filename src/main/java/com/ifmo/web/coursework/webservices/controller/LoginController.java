package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.entity.Human;
import com.ifmo.web.coursework.repository.HumanRepository;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private final HumanRepository humanRepository;

    @PostMapping("/loginok")
    public HumanResponse loginOK() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Human human = humanRepository.findByLogin(userName)
                .orElseThrow(() -> new IllegalStateException("No user " + userName + " found in DB, but present in SecurityContext"));

        return HumanResponse.fromHuman(human);
    }

    @CrossOrigin
    @PostMapping(value = "/loginerr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse loginError(@RequestParam("msg") String message) {
        return new ErrorResponse("Authentication failed: " + message);
    }

    @Autowired
    public LoginController(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }
}
