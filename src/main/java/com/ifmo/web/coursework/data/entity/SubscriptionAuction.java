package com.ifmo.web.coursework.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "subscription_auction", schema = "public", catalog = "postgres")
@IdClass(SubscriptionAuctionPK.class)
public class SubscriptionAuction {
    @Id
    @Column(name = "human_id", nullable = false)
    private Integer humanId;
    @Id
    @Column(name = "auction_id", nullable = false)
    private Integer auctionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false, insertable = false, updatable = false)
    private Human humanByHumanId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auction_id", referencedColumnName = "auction_id", nullable = false, insertable = false, updatable = false)
    private Auction auctionByAuctionId;

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

}
