package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.RecordDonation;
import com.ifmo.web.coursework.entity.RecordParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordDonationRepository extends JpaRepository<RecordDonation,Integer> {
}
