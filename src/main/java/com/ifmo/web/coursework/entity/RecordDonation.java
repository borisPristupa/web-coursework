package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "record_donation", schema = "public", catalog = "postgres")
public class RecordDonation {
    private Integer recordId;
    private Integer donationId;
    private Record recordByRecordId;
    private Donation donationByDonationId;

    @Id
    @Column(name = "record_id", nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "donation_id", nullable = false, updatable = false, insertable = false)
    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordDonation that = (RecordDonation) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(donationId, that.donationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, donationId);
    }

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    public Record getRecordByRecordId() {
        return recordByRecordId;
    }

    public void setRecordByRecordId(Record recordByRecordId) {
        this.recordByRecordId = recordByRecordId;
    }

    @ManyToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "donation_id", nullable = false)
    public Donation getDonationByDonationId() {
        return donationByDonationId;
    }

    public void setDonationByDonationId(Donation donationByDonationId) {
        this.donationByDonationId = donationByDonationId;
    }
}
