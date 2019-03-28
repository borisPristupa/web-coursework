package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "complaint_expedition", schema = "public", catalog = "postgres")
public class ComplaintExpedition {
    private Integer complaintId;
    private Integer expeditionId;
    private Complaint complaintByComplaintId;
    private Expedition expeditionByExpeditionId;

    @Id
    @Column(name = "complaint_id", nullable = false)
    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    @Basic
    @Column(name = "expedition_id", nullable = false, insertable = false, updatable = false)
    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplaintExpedition that = (ComplaintExpedition) o;
        return Objects.equals(complaintId, that.complaintId) &&
                Objects.equals(expeditionId, that.expeditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintId, expeditionId);
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
    @JoinColumn(name = "expedition_id", referencedColumnName = "expedition_id", nullable = false)
    public Expedition getExpeditionByExpeditionId() {
        return expeditionByExpeditionId;
    }

    public void setExpeditionByExpeditionId(Expedition expeditionByExpeditionId) {
        this.expeditionByExpeditionId = expeditionByExpeditionId;
    }
}
