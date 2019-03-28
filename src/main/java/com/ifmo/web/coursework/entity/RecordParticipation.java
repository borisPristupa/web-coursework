package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "record_participation", schema = "public", catalog = "postgres")
public class RecordParticipation {
    private Integer recordId;
    private Integer participationExpeditionId;
    private Record recordByRecordId;
    private ParticipationExpedition participationExpeditionByParticipationExpeditionId;

    @Id
    @Column(name = "record_id", nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "participation_expedition_id", nullable = false, insertable = false, updatable = false)
    public Integer getParticipationExpeditionId() {
        return participationExpeditionId;
    }

    public void setParticipationExpeditionId(Integer participationExpeditionId) {
        this.participationExpeditionId = participationExpeditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordParticipation that = (RecordParticipation) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(participationExpeditionId, that.participationExpeditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, participationExpeditionId);
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
    @JoinColumn(name = "participation_expedition_id", referencedColumnName = "participation_expedition_id", nullable = false)
    public ParticipationExpedition getParticipationExpeditionByParticipationExpeditionId() {
        return participationExpeditionByParticipationExpeditionId;
    }

    public void setParticipationExpeditionByParticipationExpeditionId(ParticipationExpedition participationExpeditionByParticipationExpeditionId) {
        this.participationExpeditionByParticipationExpeditionId = participationExpeditionByParticipationExpeditionId;
    }
}
