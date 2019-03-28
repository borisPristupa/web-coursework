package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Donation {
    private Integer donationId;
    private Integer humanId;
    private Integer expeditionId;
    private Timestamp time;
    private BigInteger amount;
    private Human humanByHumanId;
    private Expedition expeditionByExpeditionId;
    private Collection<RecordDonation> recordDonationsByDonationId;

    @Id
    @Column(name = "donation_id", nullable = false)
    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    @Basic
    @Column(name = "human_id", nullable = false, insertable = false, updatable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
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
    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(donationId, donation.donationId) &&
                Objects.equals(humanId, donation.humanId) &&
                Objects.equals(expeditionId, donation.expeditionId) &&
                Objects.equals(time, donation.time) &&
                Objects.equals(amount, donation.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationId, humanId, expeditionId, time, amount);
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
    @JoinColumn(name = "expedition_id", referencedColumnName = "expedition_id", nullable = false)
    public Expedition getExpeditionByExpeditionId() {
        return expeditionByExpeditionId;
    }

    public void setExpeditionByExpeditionId(Expedition expeditionByExpeditionId) {
        this.expeditionByExpeditionId = expeditionByExpeditionId;
    }

    @OneToMany(mappedBy = "donationByDonationId")
    public Collection<RecordDonation> getRecordDonationsByDonationId() {
        return recordDonationsByDonationId;
    }

    public void setRecordDonationsByDonationId(Collection<RecordDonation> recordDonationsByDonationId) {
        this.recordDonationsByDonationId = recordDonationsByDonationId;
    }
}
