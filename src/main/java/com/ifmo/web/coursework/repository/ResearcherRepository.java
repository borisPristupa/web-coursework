package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherRepository extends JpaRepository<Researcher,Integer> {
}
