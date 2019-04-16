package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.log.Log;
import com.ifmo.web.coursework.notification.Message;
import com.ifmo.web.coursework.notification.jms.CustomJMSSender;
import com.ifmo.web.coursework.webservices.exception.AlreadyExistsException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import com.ifmo.web.coursework.webservices.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/sign")
public class LoginController {
    private final HumanRepository humanRepository;
    private final HumanUtils humanUtils;
    private final PasswordEncoder encoder;

    private final CustomJMSSender jms;

    @PostMapping("/in/success")
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse signIn() {
        return HumanResponse.fromHuman(humanUtils.getCurrentHuman());
    }

    @Log.Exclude
    @PostMapping("/up")
    @ResponseStatus(HttpStatus.CREATED)
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

        try {
            humanRepository.save(human);
        } catch (Exception e) {
            logger.error("WTF", e);
            throw e;
        }

        jms.send(CustomJMSSender.MAIL, Message.builder()
                .to(email)
                .subject("Your profile")
                .text("Hello, " + firstName + "! \n" +
                        "You have successfully created an account at Archaeology! Welcome!")
                .build());

        logger.info("User '" + username + "' signed up!");
        return HumanResponse.fromHuman(human);
    }

    @Log.Exclude
    @PostMapping(value = "/err", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse signError(@RequestParam("msg") String message) {
        return new ErrorResponse("Authentication failed: " + message);
    }

    @Log.Exclude
    @RequestMapping("/out/success")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse signOut() {
        return new SuccessResponse("Successfully signed out");
    }

    @Autowired
    public LoginController(HumanRepository humanRepository, HumanUtils humanUtils, PasswordEncoder encoder, CustomJMSSender jms) {
        this.humanRepository = humanRepository;
        this.humanUtils = humanUtils;
        this.encoder = encoder;
        this.jms = jms;
    }

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
}
