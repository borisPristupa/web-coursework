package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}

