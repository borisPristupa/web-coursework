package com.ifmo.web.coursework.webservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/time")
public class TimeController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Timestamp getTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
