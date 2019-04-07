package com.ifmo.web.coursework.data.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SubscriptionExpeditionPK implements Serializable {
    private Integer humanId;
    private Integer expeditionId;

    @Column(name = "human_id", nullable = false, insertable = false, updatable = false)
    @Id
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Column(name = "expedition_id", nullable = false, updatable = false, insertable = false)
    @Id
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
        SubscriptionExpeditionPK that = (SubscriptionExpeditionPK) o;
        return Objects.equals(humanId, that.humanId) &&
                Objects.equals(expeditionId, that.expeditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId, expeditionId);
    }
}
