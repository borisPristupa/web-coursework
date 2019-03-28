package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.SubscriptionExpedition;
import com.ifmo.web.coursework.entity.SubscriptionExpeditionPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscriptionExpeditionRepository extends JpaRepository<SubscriptionExpedition, SubscriptionExpeditionPK> {
}
