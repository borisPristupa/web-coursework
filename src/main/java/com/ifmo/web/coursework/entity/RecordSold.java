package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "record_sold", schema = "public", catalog = "postgres")
public class RecordSold {
    private Integer recordId;
    private BigInteger price;
    private Integer artifactId;
    private Record recordByRecordId;
    private Artifact artifactByArtifactId;

    @Id
    @Column(name = "record_id", nullable = false)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
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
        RecordSold that = (RecordSold) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(artifactId, that.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, price, artifactId);
    }

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    public Record getRecordByRecordId() {
        return recordByRecordId;
    }

    public void setRecordByRecordId(Record recordByRecordId) {
        this.recordByRecordId = recordByRecordId;
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
