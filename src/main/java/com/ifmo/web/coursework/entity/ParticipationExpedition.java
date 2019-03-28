package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "participation_expedition", schema = "public", catalog = "postgres")
public class ParticipationExpedition {
    private Integer participationExpeditionId;
    private Integer expeditionId;
    private Integer humanId;
    private Date date;
    private Expedition expeditionByExpeditionId;
    private Human humanByHumanId;
    private Collection<RecordParticipation> recordParticipationsByParticipationExpeditionId;

    @Id
    @Column(name = "participation_expedition_id", nullable = false)
    public Integer getParticipationExpeditionId() {
        return participationExpeditionId;
    }

    public void setParticipationExpeditionId(Integer participationExpeditionId) {
        this.participationExpeditionId = participationExpeditionId;
    }

    @Basic
    @Column(name = "expedition_id", nullable = false, updatable = false, insertable = false)
    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    @Basic
    @Column(name = "human_id", nullable = false, updatable = false, insertable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Basic
    @Column(name = "_date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationExpedition that = (ParticipationExpedition) o;
        return Objects.equals(participationExpeditionId, that.participationExpeditionId) &&
                Objects.equals(expeditionId, that.expeditionId) &&
                Objects.equals(humanId, that.humanId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participationExpeditionId, expeditionId, humanId, date);
    }

    @ManyToOne
    @JoinColumn(name = "expedition_id", referencedColumnName = "expedition_id", nullable = false)
    public Expedition getExpeditionByExpeditionId() {
        return expeditionByExpeditionId;
    }

    public void setExpeditionByExpeditionId(Expedition expeditionByExpeditionId) {
        this.expeditionByExpeditionId = expeditionByExpeditionId;
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }

    @OneToMany(mappedBy = "participationExpeditionByParticipationExpeditionId")
    public Collection<RecordParticipation> getRecordParticipationsByParticipationExpeditionId() {
        return recordParticipationsByParticipationExpeditionId;
    }

    public void setRecordParticipationsByParticipationExpeditionId(Collection<RecordParticipation> recordParticipationsByParticipationExpeditionId) {
        this.recordParticipationsByParticipationExpeditionId = recordParticipationsByParticipationExpeditionId;
    }
}
