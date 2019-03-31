package com.ifmo.web.coursework.webservices.exception;

public class HumanNotFoundException extends RuntimeException {
    public HumanNotFoundException(String login) {
        super("Human not found: " + login);
    }
}
