package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.ExcavationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcavationResultRepositiory extends JpaRepository<ExcavationResult, Integer> {
    @Query("Select b from ExcavationResult a left join Stay b where b.excavations = true and b.stayId = :stay_id")
    List<ExcavationResult> findAllByStayId(@Param("stay_id") Integer stay_id);
}
