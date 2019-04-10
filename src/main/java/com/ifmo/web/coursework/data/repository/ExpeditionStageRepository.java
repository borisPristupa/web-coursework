package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.ExpeditionStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpeditionStageRepository extends JpaRepository<ExpeditionStage,Integer> {
    Optional<ExpeditionStage> findByName(String name);
}
