package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "expedition_result", schema = "public", catalog = "postgres")
public class ExpeditionResult {
    private Integer artifactId;
    private Integer resultFinderId;
    private Date date;
    private Artifact artifactByArtifactId;
    private Human resultFinderByHumanId;

    @Id
    @Column(name = "artifact_id", nullable = false, insertable = false, updatable = false)
    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    @Basic
    @Column(name = "human_id", nullable = false, insertable = false, updatable = false)
    public Integer getResultFinderId() {
        return resultFinderId;
    }

    public void setResultFinderId(Integer humanId) {
        this.resultFinderId = humanId;
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
        ExpeditionResult that = (ExpeditionResult) o;
        return Objects.equals(artifactId, that.artifactId) &&
                Objects.equals(resultFinderId, that.resultFinderId) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId, resultFinderId, date);
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
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getResultFinderByHumanId() {
        return resultFinderByHumanId;
    }

    public void setResultFinderByHumanId(Human humanByHumanId) {
        this.resultFinderByHumanId = humanByHumanId;
    }
}
