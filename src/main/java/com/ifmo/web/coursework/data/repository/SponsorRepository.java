package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor,Integer> {
}
