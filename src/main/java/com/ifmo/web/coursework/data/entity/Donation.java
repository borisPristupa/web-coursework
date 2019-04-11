package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Donation {
    private Integer donationId;
    private Integer donatorId;
    private Integer expeditionId;
    private Timestamp time;
    private Integer amount;
    private Human humanByDonator;
    private Expedition expeditionByExpeditionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id", nullable = false)
    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    @Basic
    @Column(name = "human_id", nullable = false, insertable = false, updatable = false)
    public Integer getDonatorId() {
        return donatorId;
    }

    public void setDonatorId(Integer humanId) {
        this.donatorId = humanId;
    }

    @Basic
    @Column(name = "expedition_id", nullable = false, insertable = false, updatable = false)
    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "amount", nullable = false, precision = 0)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(donationId, donation.donationId) &&
                Objects.equals(donatorId, donation.donatorId) &&
                Objects.equals(expeditionId, donation.expeditionId) &&
                Objects.equals(time, donation.time) &&
                Objects.equals(amount, donation.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationId, donatorId, expeditionId, time, amount);
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByDonator() {
        return humanByDonator;
    }

    public void setHumanByDonator(Human humanByHumanId) {
        this.humanByDonator = humanByHumanId;
    }

    @ManyToOne
    @JoinColumn(name = "expedition_id", referencedColumnName = "expedition_id", nullable = false)
    public Expedition getExpeditionByExpeditionId() {
        return expeditionByExpeditionId;
    }

    public void setExpeditionByExpeditionId(Expedition expeditionByExpeditionId) {
        this.expeditionByExpeditionId = expeditionByExpeditionId;
    }
}
