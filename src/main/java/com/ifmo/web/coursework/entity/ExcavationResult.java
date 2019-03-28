package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "excavation_result", schema = "public", catalog = "postgres")
public class ExcavationResult {
    private Integer artifactId;
    private Integer excavations;
    private Integer humanId;
    private Date date;
    private Artifact artifactByArtifactId;
    private Stay stayByExcavations;
    private Human humanByHumanId;

    @Id
    @Column(name = "artifact_id", nullable = false, insertable = false, updatable = false)
    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    @Basic
    @Column(name = "excavations", nullable = false, insertable = false, updatable = false)
    public Integer getExcavations() {
        return excavations;
    }

    public void setExcavations(Integer excavations) {
        this.excavations = excavations;
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
        ExcavationResult that = (ExcavationResult) o;
        return Objects.equals(artifactId, that.artifactId) &&
                Objects.equals(excavations, that.excavations) &&
                Objects.equals(humanId, that.humanId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId, excavations, humanId, date);
    }

    @OneToOne
    @JoinColumn(name = "artifact_id", referencedColumnName = "artifact_id", nullable = false)
    public Artifact getArtifactByArtifactId() {
        return artifactByArtifactId;
    }

    public void setArtifactByArtifactId(Artifact artifactByArtifactId) {
        this.artifactByArtifactId = artifactByArtifactId;
    }

    @ManyToOne
    @JoinColumn(name = "excavations", referencedColumnName = "stay_id", nullable = false)
    public Stay getStayByExcavations() {
        return stayByExcavations;
    }

    public void setStayByExcavations(Stay stayByExcavations) {
        this.stayByExcavations = stayByExcavations;
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }
}
