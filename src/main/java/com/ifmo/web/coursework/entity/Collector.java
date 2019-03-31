package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Collector {
    private Integer humanId;
    private Human humanByCollector;

    @Id
    @Column(name = "human_id", nullable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collector collector = (Collector) o;
        return Objects.equals(humanId, collector.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId);
    }

    @OneToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByCollector() {
        return humanByCollector;
    }

    public void setHumanByCollector(Human humanByHumanId) {
        this.humanByCollector = humanByHumanId;
    }
}
