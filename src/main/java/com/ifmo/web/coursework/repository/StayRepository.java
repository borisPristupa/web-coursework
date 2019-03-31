package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Route;
import com.ifmo.web.coursework.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Integer> {
    List<Stay> findAllByRouteByRouteIdOrderByStartDate(Route route);
}
