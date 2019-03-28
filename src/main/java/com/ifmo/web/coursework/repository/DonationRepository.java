package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.security.PermitAll;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation,Integer> {
    List<Donation> findAllByHumanIdOrderByTime(Integer humanId);

    @Query("select a from Donation a left join Expedition b on a.expeditionId = b.expeditionId order by a.amount ")
    List<Donation> findAllByHumanIdAndExpeditionIdOrderByAmount();
}
