package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Age {
    private Integer ageId;
    private String description;
    private Collection<Artifact> artifactsByAgeId;

    @Id
    @Column(name = "age_id", nullable = false)
    public Integer getAgeId() {
        return ageId;
    }

    public void setAgeId(Integer ageId) {
        this.ageId = ageId;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Age age = (Age) o;
        return Objects.equals(ageId, age.ageId) &&
                Objects.equals(description, age.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ageId, description);
    }

    @OneToMany(mappedBy = "ageByAgeId")
    public Collection<Artifact> getArtifactsByAgeId() {
        return artifactsByAgeId;
    }

    public void setArtifactsByAgeId(Collection<Artifact> artifactsByAgeId) {
        this.artifactsByAgeId = artifactsByAgeId;
    }
}
