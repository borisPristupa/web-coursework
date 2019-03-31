package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.exception.HumanNotFoundException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HumanNotFoundHandler {
    @ResponseBody
    @ExceptionHandler(HumanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse pageNotFoundHandler(HumanNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
