package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Country;
import com.ifmo.web.coursework.data.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCountryNames() {
        return countryRepository.findAll().stream()
                .map(Country::getName)
                .collect(Collectors.toList());
    }

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}
