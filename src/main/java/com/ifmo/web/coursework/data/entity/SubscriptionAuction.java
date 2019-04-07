package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subscription_auction", schema = "public", catalog = "postgres")
@IdClass(SubscriptionAuctionPK.class)
public class SubscriptionAuction {
    private Integer humanId;
    private Integer auctionId;
    private Human humanByHumanId;
    private Auction auctionByAuctionId;

    @Id
    @Column(name = "human_id", nullable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Id
    @Column(name = "auction_id", nullable = false)
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
        SubscriptionAuction that = (SubscriptionAuction) o;
        return Objects.equals(humanId, that.humanId) &&
                Objects.equals(auctionId, that.auctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId, auctionId);
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "auction_id", nullable = false)
    public Auction getAuctionByAuctionId() {
        return auctionByAuctionId;
    }

    public void setAuctionByAuctionId(Auction auctionByAuctionId) {
        this.auctionByAuctionId = auctionByAuctionId;
    }
}
