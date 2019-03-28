package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Artifact;
import com.ifmo.web.coursework.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.util.List;

public interface ExpeditionRepository extends JpaRepository<Expedition,Integer> {
    List<Expedition> findAllByName(String name);
    List<Expedition> findAllByStageId(Integer stage_id);
    List<Expedition> findAllByBanned(Boolean banned);

    @Query("select a from Expedition a join ParticipationExpedition b on a.expeditionId = b.expeditionId where b.humanId = :humanid")
    List<Expedition> findAllByHumanId(@Param("humanid") Integer humanId);

    @Query("select a from Expedition a join SubscriptionExpedition b on a.expeditionId = b.expeditionId where b.humanId = :humanid")
    List<Expedition> findAllByHumanIdSubscrittionExpedition(@Param("humanid") Integer humanId);

    @Query("Select a from Expedition a  where  a.costs between :price1 and :price2")
    List<Artifact> pricebetween(@Param("price1") Integer price1, @Param("price2") Integer price2);

    @Query("select a from Expedition a join ParticipationExpedition b on a.expeditionId = b.expeditionId order by count(b.humanId)")
    List<Expedition> findAllByCountHumanId();

    @Query("select a from Expedition a join Stay b on a.routePlan = b.routeId order by b.startDate ")
    List<Expedition> findAllByStayOrderByStartDate();

    @Query("select a from Expedition a join Stay b on a.routePlan = b.routeId where extract(b.startDate from b) = :start_date")
    List<Expedition> findAllByStay(@Param("start_date") Integer startdate);
}
