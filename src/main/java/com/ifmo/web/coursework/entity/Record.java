package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Record {
    private Integer recordId;
    private String type;
    private Integer humanId;
    private Timestamp time;
    private Human humanByHumanId;
    private RecordDonation recordDonationByRecordId;
    private RecordParticipation recordParticipationByRecordId;
    private RecordPurchased recordPurchasedByRecordId;
    private RecordSold recordSoldByRecordId;

    @Id
    @Column(name = "record_id", nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(recordId, record.recordId) &&
                Objects.equals(type, record.type) &&
                Objects.equals(humanId, record.humanId) &&
                Objects.equals(time, record.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, type, humanId, time);
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }

    @OneToOne(mappedBy = "recordByRecordId")
    public RecordDonation getRecordDonationByRecordId() {
        return recordDonationByRecordId;
    }

    public void setRecordDonationByRecordId(RecordDonation recordDonationByRecordId) {
        this.recordDonationByRecordId = recordDonationByRecordId;
    }

    @OneToOne(mappedBy = "recordByRecordId")
    public RecordParticipation getRecordParticipationByRecordId() {
        return recordParticipationByRecordId;
    }

    public void setRecordParticipationByRecordId(RecordParticipation recordParticipationByRecordId) {
        this.recordParticipationByRecordId = recordParticipationByRecordId;
    }

    @OneToOne(mappedBy = "recordByRecordId")
    public RecordPurchased getRecordPurchasedByRecordId() {
        return recordPurchasedByRecordId;
    }

    public void setRecordPurchasedByRecordId(RecordPurchased recordPurchasedByRecordId) {
        this.recordPurchasedByRecordId = recordPurchasedByRecordId;
    }

    @OneToOne(mappedBy = "recordByRecordId")
    public RecordSold getRecordSoldByRecordId() {
        return recordSoldByRecordId;
    }

    public void setRecordSoldByRecordId(RecordSold recordSoldByRecordId) {
        this.recordSoldByRecordId = recordSoldByRecordId;
    }
}
