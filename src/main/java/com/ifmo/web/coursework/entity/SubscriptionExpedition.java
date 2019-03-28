package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subscription_expedition", schema = "public", catalog = "postgres")
@IdClass(SubscriptionExpeditionPK.class)
public class SubscriptionExpedition {
    private Integer humanId;
    private Integer expeditionId;
    private Human humanByHumanId;
    private Expedition expeditionByExpeditionId;

    @Id
    @Column(name = "human_id", nullable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Id
    @Column(name = "expedition_id", nullable = false)
    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionExpedition that = (SubscriptionExpedition) o;
        return Objects.equals(humanId, that.humanId) &&
                Objects.equals(expeditionId, that.expeditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId, expeditionId);
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
}
