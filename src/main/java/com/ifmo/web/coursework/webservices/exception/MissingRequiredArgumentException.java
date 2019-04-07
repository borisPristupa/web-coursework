package com.ifmo.web.coursework.webservices.exception;

public class MissingRequiredArgumentException extends RuntimeException {
    public MissingRequiredArgumentException(String... arguments) {
        super("Missing required argument" + (arguments.length > 1 ? "s: " : ": ") + String.join(", ", arguments));
    }
}
