package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherRepository extends JpaRepository<Researcher,Integer> {
}
