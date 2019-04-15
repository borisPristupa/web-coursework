package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandle(Exception e) {
        logger.error("Handling internal error", e);
        return new ErrorResponse("Internal server error: " + e.getMessage());
    }

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
}
