package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "complaint_artifact", schema = "public", catalog = "postgres")
public class ComplaintArtifact {
    private Integer complaintId;
    private Integer artifactId;
    private Complaint complaintByComplaintId;
    private Artifact artifactByArtifactId;

    @Id
    @Column(name = "complaint_id", nullable = false)
    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    @Basic
    @Column(name = "artifact_id", nullable = false, insertable = false, updatable = false)
    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintArtifact that = (ComplaintArtifact) o;
        return Objects.equals(complaintId, that.complaintId) &&
                Objects.equals(artifactId, that.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintId, artifactId);
    }

    @OneToOne
    @JoinColumn(name = "complaint_id", referencedColumnName = "complaint_id", nullable = false)
    public Complaint getComplaintByComplaintId() {
        return complaintByComplaintId;
    }

    public void setComplaintByComplaintId(Complaint complaintByComplaintId) {
        this.complaintByComplaintId = complaintByComplaintId;
    }

    @ManyToOne
    @JoinColumn(name = "artifact_id", referencedColumnName = "artifact_id", nullable = false)
    public Artifact getArtifactByArtifactId() {
        return artifactByArtifactId;
    }

    public void setArtifactByArtifactId(Artifact artifactByArtifactId) {
        this.artifactByArtifactId = artifactByArtifactId;
    }
}
