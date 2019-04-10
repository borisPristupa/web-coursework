package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Age;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgeRepository extends JpaRepository<Age, Integer> {
    Optional<Age> findByDescription(String description);
}
