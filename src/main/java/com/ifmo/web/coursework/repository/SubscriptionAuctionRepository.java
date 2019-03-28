package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.SubscriptionAuction;
import com.ifmo.web.coursework.entity.SubscriptionExpeditionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionAuctionRepository extends JpaRepository<SubscriptionAuction, SubscriptionExpeditionPK> {
}
