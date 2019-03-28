package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}

