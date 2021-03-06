package com.ifmo.web.coursework.webservices.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BadCredentialsHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        try {
            httpServletRequest.getRequestDispatcher("/sign/err?msg=" + e.getMessage())
                    .forward(httpServletRequest, httpServletResponse);
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }
}
