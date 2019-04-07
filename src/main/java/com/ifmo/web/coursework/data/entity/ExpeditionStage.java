package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "expedition_stage", schema = "public", catalog = "postgres")
public class ExpeditionStage {
    private Integer stageId;
    private String name;
    private Collection<Expedition> expeditionsByStageId;

    @Id
    @Column(name = "stage_id", nullable = false)
    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpeditionStage that = (ExpeditionStage) o;
        return Objects.equals(stageId, that.stageId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stageId, name);
    }

    @OneToMany(mappedBy = "expeditionStageByStageId")
    public Collection<Expedition> getExpeditionsByStageId() {
        return expeditionsByStageId;
    }

    public void setExpeditionsByStageId(Collection<Expedition> expeditionsByStageId) {
        this.expeditionsByStageId = expeditionsByStageId;
    }
}
