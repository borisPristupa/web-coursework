package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Country {
    private Integer countryId;
    private String name;
    private Collection<Artifact> artifactsByCountryId;
    private Collection<Artifact> artifactsByCountryId_0;
    private Collection<Human> humansByCountryId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(countryId, country.countryId) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, name);
    }

    @OneToMany(mappedBy = "countryByOrigin")
    public Collection<Artifact> getArtifactsByCountryId() {
        return artifactsByCountryId;
    }

    public void setArtifactsByCountryId(Collection<Artifact> artifactsByCountryId) {
        this.artifactsByCountryId = artifactsByCountryId;
    }

    @OneToMany(mappedBy = "countryByCountryId")
    public Collection<Artifact> getArtifactsByCountryId_0() {
        return artifactsByCountryId_0;
    }

    public void setArtifactsByCountryId_0(Collection<Artifact> artifactsByCountryId_0) {
        this.artifactsByCountryId_0 = artifactsByCountryId_0;
    }

    @OneToMany(mappedBy = "countryByCountryId")
    public Collection<Human> getHumansByCountryId() {
        return humansByCountryId;
    }

    public void setHumansByCountryId(Collection<Human> humansByCountryId) {
        this.humansByCountryId = humansByCountryId;
    }
}
