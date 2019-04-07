package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Route;
import com.ifmo.web.coursework.data.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Integer> {
    List<Stay> findAllByRouteByRouteIdOrderByStartDate(Route route);
}
