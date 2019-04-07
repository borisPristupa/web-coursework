package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MissingRequiredArgumentHandler {
    @ResponseBody
    @ExceptionHandler(MissingRequiredArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingRequiredArgumentHandler(MissingRequiredArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }
}
