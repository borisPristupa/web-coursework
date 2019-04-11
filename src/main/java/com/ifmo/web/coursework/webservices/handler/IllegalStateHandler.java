package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalStateHandler {
    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse illegalStateHandler(IllegalStateException e) {
        logger.error("Illegal server state while responding request", e);
        return new ErrorResponse("Wrong state of server: " + e.getMessage());
    }

    private static Logger logger = LoggerFactory.getLogger(IllegalStateHandler.class);
}
