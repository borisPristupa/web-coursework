package com.ifmo.web.coursework.webservices.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("Page not found");
    }
}
