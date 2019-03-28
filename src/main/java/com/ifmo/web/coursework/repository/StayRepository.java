package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Integer> {
    @Query("select a from Stay a where  a.routeId = :route_id order by a.startDate")
    List<Stay> findAllOrderByStartDate(@Param("route_id") Integer route_id);

    @Query("select a from Stay a where  a.routeId = :route_id and a.excavations = TRUE order by a.startDate")
    List<Stay> findAllOrderByStartDateSortexcavationeqTrue(@Param("route_id") Integer route_id);
}
