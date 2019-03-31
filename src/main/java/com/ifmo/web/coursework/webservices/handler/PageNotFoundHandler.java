package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.exception.PageNotFoundException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PageNotFoundHandler {
    @ResponseBody
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse pageNotFoundHandler(PageNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
