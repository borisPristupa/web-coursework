package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Record findByRecordId(Integer record_id);
    List<Record> findAllByType(String type);
    List<Record> findAllByHumanId(Integer human_id);
    List<Record> findAllByHumanIdOrderByTime(Integer humanId);
}
