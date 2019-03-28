package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.ParticipationExpedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationExpeditionRepository extends JpaRepository<ParticipationExpedition,Integer> {
    @Query("select a from ParticipationExpedition a join Human b on a.humanId = b.humanId where b.humanId = :humanid order by a.date")
    List<ParticipationExpedition> findAllHumanOrderByDate(@Param("humanid") Integer humanId);

    @Query("select a from ParticipationExpedition a where a.expeditionId = :expeditionid order by a.expeditionId")
    List<ParticipationExpedition> findAllOrderByExpeditionId(@Param("expeditionid") Integer expeditionId);
}
