package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.SubscriptionAuction;
import com.ifmo.web.coursework.data.entity.SubscriptionAuctionPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionAuctionRepository extends JpaRepository<SubscriptionAuction, SubscriptionAuctionPK> {
    List<SubscriptionAuction> findAllByAuctionId(Integer auctionId);
}
