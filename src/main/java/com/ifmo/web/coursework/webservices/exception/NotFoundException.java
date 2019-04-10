package com.ifmo.web.coursework.webservices.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String login) {
        super("Resource not found: " + login);
    }
}
