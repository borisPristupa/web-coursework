package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DataIntegrityViolationHandler {
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse dataIntegrityHandler(DataIntegrityViolationException e) {
        logger.error("Database error while responding request", e);
        return new ErrorResponse("Database error: " + e.getMessage());
    }

    private static Logger logger = LoggerFactory.getLogger(DataIntegrityViolationHandler.class);
}


