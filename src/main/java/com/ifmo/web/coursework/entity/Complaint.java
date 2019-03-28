package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Complaint {
    private Integer complaintId;
    private String type;
    private Integer messageId;
    private Message messageByMessageId;
    private ComplaintArtifact complaintArtifactByComplaintId;
    private ComplaintExpedition complaintExpeditionByComplaintId;
    private ComplaintHuman complaintHumanByComplaintId;

    @Id
    @Column(name = "complaint_id", nullable = false)
    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "message_id", nullable = false, insertable = false, updatable = false)
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return Objects.equals(complaintId, complaint.complaintId) &&
                Objects.equals(type, complaint.type) &&
                Objects.equals(messageId, complaint.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintId, type, messageId);
    }

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false)
    public Message getMessageByMessageId() {
        return messageByMessageId;
    }

    public void setMessageByMessageId(Message messageByMessageId) {
        this.messageByMessageId = messageByMessageId;
    }

    @OneToOne(mappedBy = "complaintByComplaintId")
    public ComplaintArtifact getComplaintArtifactByComplaintId() {
        return complaintArtifactByComplaintId;
    }

    public void setComplaintArtifactByComplaintId(ComplaintArtifact complaintArtifactByComplaintId) {
        this.complaintArtifactByComplaintId = complaintArtifactByComplaintId;
    }

    @OneToOne(mappedBy = "complaintByComplaintId")
    public ComplaintExpedition getComplaintExpeditionByComplaintId() {
        return complaintExpeditionByComplaintId;
    }

    public void setComplaintExpeditionByComplaintId(ComplaintExpedition complaintExpeditionByComplaintId) {
        this.complaintExpeditionByComplaintId = complaintExpeditionByComplaintId;
    }

    @OneToOne(mappedBy = "complaintByComplaintId")
    public ComplaintHuman getComplaintHumanByComplaintId() {
        return complaintHumanByComplaintId;
    }

    public void setComplaintHumanByComplaintId(ComplaintHuman complaintHumanByComplaintId) {
        this.complaintHumanByComplaintId = complaintHumanByComplaintId;
    }
}
