package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.RecordSold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordSoldRepository extends JpaRepository<RecordSold,Integer> {
}
