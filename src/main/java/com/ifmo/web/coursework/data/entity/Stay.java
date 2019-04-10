package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Stay {
    private Integer stayId;
    private Integer routeId;
    private Date startDate;
    private Date endDate;
    private Double latitude;
    private Double longtitude;
    private Route routeByRouteId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stay_id", nullable = false)
    public Integer getStayId() {
        return stayId;
    }

    public void setStayId(Integer stayId) {
        this.stayId = stayId;
    }

    @Basic
    @Column(name = "route_id", nullable = false, insertable = false, updatable = false)
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longtitude", nullable = false, precision = 0)
    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stay stay = (Stay) o;
        return Objects.equals(stayId, stay.stayId) &&
                Objects.equals(routeId, stay.routeId) &&
                Objects.equals(startDate, stay.startDate) &&
                Objects.equals(endDate, stay.endDate) &&
                Objects.equals(latitude, stay.latitude) &&
                Objects.equals(longtitude, stay.longtitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stayId, routeId, startDate, endDate, latitude, longtitude);
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id", nullable = false)
    public Route getRouteByRouteId() {
        return routeByRouteId;
    }

    public void setRouteByRouteId(Route routeByRouteId) {
        this.routeByRouteId = routeByRouteId;
    }
}
