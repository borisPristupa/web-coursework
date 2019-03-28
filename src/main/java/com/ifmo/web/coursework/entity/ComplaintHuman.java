package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "complaint_human", schema = "public", catalog = "postgres")
public class ComplaintHuman {
    private Integer complaintId;
    private Integer humanId;
    private Complaint complaintByComplaintId;
    private Human humanByHumanId;

    @Id
    @Column(name = "complaint_id", nullable = false)
    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    @Basic
    @Column(name = "human_id", nullable = false, insertable = false, updatable = false)
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
        ComplaintHuman that = (ComplaintHuman) o;
        return Objects.equals(complaintId, that.complaintId) &&
                Objects.equals(humanId, that.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintId, humanId);
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
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }
}
