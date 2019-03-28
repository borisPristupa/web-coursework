package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.RecordPurchased;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordPurchasedRepository extends JpaRepository<RecordPurchased,Integer> {
}
