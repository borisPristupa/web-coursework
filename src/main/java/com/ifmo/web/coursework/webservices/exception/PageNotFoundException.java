package com.ifmo.web.coursework.webservices.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String path) {
        super("Page not found: " + path);
    }
}
