package com.ifmo.web.coursework.webservices.handler;

import com.ifmo.web.coursework.webservices.exception.PermissionDeniedException;
import com.ifmo.web.coursework.webservices.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RestControllerAdvice
public class PermissionDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping("/forbidden")
    @ExceptionHandler(PermissionDeniedException.class)
    public ErrorResponse permissionDeniedHandle(PermissionDeniedException e) {
        return new ErrorResponse("Permission denied: " + e.getMessage());
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletRequest.getRequestDispatcher("/forbidden").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletRequest.getRequestDispatcher("/forbidden").forward(httpServletRequest, httpServletResponse);
    }
}
