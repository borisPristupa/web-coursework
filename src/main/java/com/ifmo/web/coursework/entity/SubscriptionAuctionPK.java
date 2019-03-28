package com.ifmo.web.coursework.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SubscriptionAuctionPK implements Serializable {
    private Integer humanId;
    private Integer auctionId;

    @Column(name = "human_id", nullable = false, updatable = false, insertable = false)
    @Id
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Column(name = "auction_id", nullable = false, updatable = false, insertable = false)
    @Id
    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionAuctionPK that = (SubscriptionAuctionPK) o;
        return Objects.equals(humanId, that.humanId) &&
                Objects.equals(auctionId, that.auctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId, auctionId);
    }
}
