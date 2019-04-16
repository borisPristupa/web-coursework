package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Expedition {
    private Integer expeditionId;
    private String name;
    private String description;
    private byte[] avatarSmall;
    private byte[] avatarFull;
    private Integer costs;
    private Integer currentSum;
    private Integer stageId;
    private Boolean banned;
    private Integer routePlan;
    private Integer routeCurrent;
    private Integer head;
    private Collection<Donation> donationsByExpeditionId;
    private ExpeditionStage expeditionStageByStageId;
    private Route routeByRoutePlan;
    private Route routeByRouteCurrent;
    private Human humanByHead;
    private Collection<ParticipationExpedition> participationExpeditionsByExpeditionId = new ArrayList<>();
    private Collection<SubscriptionExpedition> subscriptionExpeditionsByExpeditionId = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expedition_id", nullable = false)
    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "avatar_small", nullable = true)
    public byte[] getAvatarSmall() {
        return avatarSmall;
    }

    public void setAvatarSmall(byte[] avatarSmall) {
        this.avatarSmall = avatarSmall;
    }

    @Basic
    @Column(name = "avatar_full", nullable = true)
    public byte[] getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(byte[] avatarFull) {
        this.avatarFull = avatarFull;
    }

    @Basic
    @Column(name = "costs", nullable = false, precision = 0)
    public Integer getCosts() {
        return costs;
    }

    public void setCosts(Integer costs) {
        this.costs = costs;
    }

    @Basic
    @Column(name = "current_sum", nullable = false, precision = 0)
    public Integer getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(Integer currentSum) {
        this.currentSum = currentSum;
    }

    @Basic
    @Column(name = "stage_id", nullable = false, insertable = false, updatable = false)
    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    @Basic
    @Column(name = "banned", nullable = false)
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Basic
    @Column(name = "route_plan", nullable = true, insertable = false, updatable = false)
    public Integer getRoutePlan() {
        return routePlan;
    }

    public void setRoutePlan(Integer routePlan) {
        this.routePlan = routePlan;
    }

    @Basic
    @Column(name = "route_current", nullable = true, insertable = false, updatable = false)
    public Integer getRouteCurrent() {
        return routeCurrent;
    }

    public void setRouteCurrent(Integer routeCurrent) {
        this.routeCurrent = routeCurrent;
    }

    @Basic
    @Column(name = "head", nullable = false, insertable = false, updatable = false)
    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expedition that = (Expedition) o;
        return Objects.equals(expeditionId, that.expeditionId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Arrays.equals(avatarSmall, that.avatarSmall) &&
                Arrays.equals(avatarFull, that.avatarFull) &&
                Objects.equals(costs, that.costs) &&
                Objects.equals(currentSum, that.currentSum) &&
                Objects.equals(stageId, that.stageId) &&
                Objects.equals(banned, that.banned) &&
                Objects.equals(routePlan, that.routePlan) &&
                Objects.equals(routeCurrent, that.routeCurrent) &&
                Objects.equals(head, that.head);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(expeditionId, name, description, costs, currentSum, stageId, banned, routePlan, routeCurrent, head);
        result = 31 * result + Arrays.hashCode(avatarSmall);
        result = 31 * result + Arrays.hashCode(avatarFull);
        return result;
    }

    @OneToMany(mappedBy = "expeditionByExpeditionId")
    public Collection<Donation> getDonationsByExpeditionId() {
        return donationsByExpeditionId;
    }

    public void setDonationsByExpeditionId(Collection<Donation> donationsByExpeditionId) {
        this.donationsByExpeditionId = donationsByExpeditionId;
    }

    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id", nullable = false)
    public ExpeditionStage getExpeditionStageByStageId() {
        return expeditionStageByStageId;
    }

    public void setExpeditionStageByStageId(ExpeditionStage expeditionStageByStageId) {
        this.expeditionStageByStageId = expeditionStageByStageId;
    }

    @ManyToOne
    @JoinColumn(name = "route_plan", referencedColumnName = "route_id")
    public Route getRouteByRoutePlan() {
        return routeByRoutePlan;
    }

    public void setRouteByRoutePlan(Route routeByRoutePlan) {
        this.routeByRoutePlan = routeByRoutePlan;
    }

    @ManyToOne
    @JoinColumn(name = "route_current", referencedColumnName = "route_id")
    public Route getRouteByRouteCurrent() {
        return routeByRouteCurrent;
    }

    public void setRouteByRouteCurrent(Route routeByRouteCurrent) {
        this.routeByRouteCurrent = routeByRouteCurrent;
    }

    @ManyToOne
    @JoinColumn(name = "head", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHead() {
        return humanByHead;
    }

    public void setHumanByHead(Human humanByHead) {
        this.humanByHead = humanByHead;
    }

    @OneToMany(mappedBy = "expeditionByExpeditionId")
    public Collection<ParticipationExpedition> getParticipationExpeditionsByExpeditionId() {
        return participationExpeditionsByExpeditionId;
    }

    public void setParticipationExpeditionsByExpeditionId(Collection<ParticipationExpedition> participationExpeditionsByExpeditionId) {
        this.participationExpeditionsByExpeditionId = participationExpeditionsByExpeditionId;
    }

    @OneToMany(mappedBy = "expeditionByExpeditionId")
    public Collection<SubscriptionExpedition> getSubscriptionExpeditionsByExpeditionId() {
        return subscriptionExpeditionsByExpeditionId;
    }

    public void setSubscriptionExpeditionsByExpeditionId(Collection<SubscriptionExpedition> subscriptionExpeditionsByExpeditionId) {
        this.subscriptionExpeditionsByExpeditionId = subscriptionExpeditionsByExpeditionId;
    }
}
