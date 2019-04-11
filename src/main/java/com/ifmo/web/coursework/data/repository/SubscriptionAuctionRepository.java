package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.SubscriptionAuction;
import com.ifmo.web.coursework.data.entity.SubscriptionExpeditionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionAuctionRepository extends JpaRepository<SubscriptionAuction, SubscriptionExpeditionPK> {
}
