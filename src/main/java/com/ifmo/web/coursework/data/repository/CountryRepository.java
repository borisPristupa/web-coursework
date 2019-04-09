package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    Optional<Country> findByName(String name);
}

