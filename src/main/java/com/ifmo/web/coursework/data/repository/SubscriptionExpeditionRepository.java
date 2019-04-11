package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.SubscriptionExpedition;
import com.ifmo.web.coursework.data.entity.SubscriptionExpeditionPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscriptionExpeditionRepository extends JpaRepository<SubscriptionExpedition, SubscriptionExpeditionPK> {
}
