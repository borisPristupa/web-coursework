package com.ifmo.web.coursework.webservices.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String login) {
        super("Human not found: " + login);
    }
}
