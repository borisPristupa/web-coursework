package com.ifmo.web.coursework.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "subscription_expedition", schema = "public", catalog = "postgres")
@IdClass(SubscriptionExpeditionPK.class)
public class SubscriptionExpedition {

    @Id
    @Column(name = "expedition_id")
    private Integer expeditionId;
    @Id
    @Column(name = "human_id")
    private Integer humanId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false, updatable = false, insertable = false)
    private Human humanByHumanId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expedition_id", referencedColumnName = "expedition_id", nullable = false,  updatable = false, insertable = false)
    private Expedition expeditionByExpeditionId;

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

}
