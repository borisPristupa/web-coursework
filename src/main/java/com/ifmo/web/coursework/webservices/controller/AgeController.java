package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Age;
import com.ifmo.web.coursework.data.repository.AgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/age")
public class AgeController {
    private final AgeRepository ageRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAges() {
        return ageRepository.findAll().stream()
                .map(Age::getDescription)
                .collect(Collectors.toList());
    }

    @Autowired
    public AgeController(AgeRepository ageRepository) {
        this.ageRepository = ageRepository;
    }
}
