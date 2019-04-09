package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Auction {
    private Integer auctionId;
    private Integer artifactId;
    private Integer priceOld;
    private Integer priceNew;
    private Integer raiser;
    private Timestamp betTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private Artifact artifactByArtifactId;
    private Human humanByRaiser;
    private Collection<SubscriptionAuction> subscriptionAuctionsByAuctionId;

    @Id
    @Column(name = "auction_id", nullable = false)
    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "artifact_id", nullable = false, updatable = false, insertable = false)
    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    @Basic
    @Column(name = "price_old", nullable = true, precision = 0)
    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    @Basic
    @Column(name = "price_new", nullable = false, precision = 0)
    public Integer getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(Integer priceNew) {
        this.priceNew = priceNew;
    }

    @Basic
    @Column(name = "raiser", nullable = true, insertable = false, updatable = false)
    public Integer getRaiser() {
        return raiser;
    }

    public void setRaiser(Integer raiser) {
        this.raiser = raiser;
    }

    @Basic
    @Column(name = "bet_time", nullable = true)
    public Timestamp getBetTime() {
        return betTime;
    }

    public void setBetTime(Timestamp betTime) {
        this.betTime = betTime;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return Objects.equals(auctionId, auction.auctionId) &&
                Objects.equals(artifactId, auction.artifactId) &&
                Objects.equals(priceOld, auction.priceOld) &&
                Objects.equals(priceNew, auction.priceNew) &&
                Objects.equals(raiser, auction.raiser) &&
                Objects.equals(betTime, auction.betTime) &&
                Objects.equals(startTime, auction.startTime) &&
                Objects.equals(endTime, auction.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, artifactId, priceOld, priceNew, raiser, betTime, startTime, endTime);
    }

    @OneToOne
    @JoinColumn(name = "artifact_id", referencedColumnName = "artifact_id", nullable = false)
    public Artifact getArtifactByArtifactId() {
        return artifactByArtifactId;
    }

    public void setArtifactByArtifactId(Artifact artifactByArtifactId) {
        this.artifactByArtifactId = artifactByArtifactId;
    }

    @ManyToOne
    @JoinColumn(name = "raiser", referencedColumnName = "human_id")
    public Human getHumanByRaiser() {
        return humanByRaiser;
    }

    public void setHumanByRaiser(Human humanByRaiser) {
        this.humanByRaiser = humanByRaiser;
    }

    @OneToMany(mappedBy = "auctionByAuctionId")
    public Collection<SubscriptionAuction> getSubscriptionAuctionsByAuctionId() {
        return subscriptionAuctionsByAuctionId;
    }

    public void setSubscriptionAuctionsByAuctionId(Collection<SubscriptionAuction> subscriptionAuctionsByAuctionId) {
        this.subscriptionAuctionsByAuctionId = subscriptionAuctionsByAuctionId;
    }
}
