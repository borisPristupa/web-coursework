package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.entity.Human;
import com.ifmo.web.coursework.repository.HumanRepository;
import com.ifmo.web.coursework.webservices.exception.AlreadyExistsException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import com.ifmo.web.coursework.webservices.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign")
public class LoginController {
    private final HumanRepository humanRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/in/success")
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse signIn() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Human human = humanRepository.findByLogin(userName)
                .orElseThrow(() -> new IllegalStateException("No user " + userName + " found in DB, but present in SecurityContext"));

        return HumanResponse.fromHuman(human);
    }

    @PutMapping("/up")
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse signUp(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email,
                                @RequestParam("first_name") String firstName,
                                @RequestParam("second_name") String secondName,
                                @RequestParam(value = "last_name", required = false) String lastName,
                                @RequestParam(value = "vk_id", required = false) String vk_id) {
        if (humanRepository.findByLogin(username).isPresent())
            throw new AlreadyExistsException("User with such username already exists");

        if (humanRepository.findByEmail(email).isPresent())
            throw new AlreadyExistsException("User with such email already exists");

        Human human = new Human();
        human.setPassword(encoder.encode(password));
        human.setEmail(email);
        human.setLogin(username);
        human.setFirstName(firstName);
        human.setSecondName(secondName);
        human.setLastName(lastName);
        human.setVkId(vk_id);

        humanRepository.save(human);

        return HumanResponse.fromHuman(human);
    }

    @PostMapping(value = "/err", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse signError(@RequestParam("msg") String message) {
        return new ErrorResponse("Authentication failed: " + message);
    }

    @RequestMapping("/out/success")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse signOut() {
        return new SuccessResponse("Successfully signed out");
    }

    @Autowired
    public LoginController(HumanRepository humanRepository, PasswordEncoder encoder) {
        this.humanRepository = humanRepository;
        this.encoder = encoder;
    }
}
