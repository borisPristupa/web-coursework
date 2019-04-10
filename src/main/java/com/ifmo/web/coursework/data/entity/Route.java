package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Route {
    private Integer routeId;
    private Collection<Expedition> expeditionsByRouteId;
    private Collection<Expedition> expeditionsByRouteId_0;
    private Collection<Stay> staysByRouteId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false)
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(routeId, route.routeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId);
    }

    @OneToMany(mappedBy = "routeByRoutePlan")
    public Collection<Expedition> getExpeditionsByRouteId() {
        return expeditionsByRouteId;
    }

    public void setExpeditionsByRouteId(Collection<Expedition> expeditionsByRouteId) {
        this.expeditionsByRouteId = expeditionsByRouteId;
    }

    @OneToMany(mappedBy = "routeByRouteCurrent")
    public Collection<Expedition> getExpeditionsByRouteId_0() {
        return expeditionsByRouteId_0;
    }

    public void setExpeditionsByRouteId_0(Collection<Expedition> expeditionsByRouteId_0) {
        this.expeditionsByRouteId_0 = expeditionsByRouteId_0;
    }

    @OneToMany(mappedBy = "routeByRouteId")
    public Collection<Stay> getStaysByRouteId() {
        return staysByRouteId;
    }

    public void setStaysByRouteId(Collection<Stay> staysByRouteId) {
        this.staysByRouteId = staysByRouteId;
    }
}
