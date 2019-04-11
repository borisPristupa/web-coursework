package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation,Integer> {
    List<Donation> findAllByDonatorIdOrderByTime(Integer humanId);

    @Query("select a from Donation a left join Expedition b on a.expeditionId = b.expeditionId order by a.amount ")
    List<Donation> findAllByHumanIdAndExpeditionIdOrderByAmount();
}
