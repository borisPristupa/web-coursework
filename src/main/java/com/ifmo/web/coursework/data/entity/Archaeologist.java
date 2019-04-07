package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Archaeologist {
    private Integer humanId;
    private Human humanByHumanId;

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
        Archaeologist that = (Archaeologist) o;
        return Objects.equals(humanId, that.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId);
    }

    @OneToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }
}
