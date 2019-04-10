package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Artifact {
    private Integer artifactId;
    private String name;
    private Boolean approved;
    private String description ="";
    private Integer ageId;
    private byte[] avatarSmall;
    private byte[] avatarFull;
    private Integer owner;
    private Integer approver;
    private Integer origin;
    private Integer countryId;
    private Integer categoryId;
    private Boolean banned;
    private Age ageByAgeId;
    private Human humanByOwner;
    private Human humanByApprover;
    private Country countryByOrigin;
    private Country countryByCountryId;
    private Category categoryByCategoryId;
    private Auction auctionByArtifactId;
    private ExpeditionResult expeditionResultByArtifactId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artifact_id", nullable = false)
    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
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
    @Column(name = "approved", nullable = false)
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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
    @Column(name = "age_id", nullable = true, insertable = false, updatable = false)
    public Integer getAgeId() {
        return ageId;
    }

    public void setAgeId(Integer ageId) {
        this.ageId = ageId;
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
    @Column(name = "owner", nullable = false, insertable = false, updatable = false)
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "approver", nullable = false, insertable = false, updatable = false)
    public Integer getApprover() {
        return approver;
    }

    public void setApprover(Integer approver) {
        this.approver = approver;
    }

    @Basic
    @Column(name = "origin", nullable = true, insertable = false, updatable = false)
    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "country_id", nullable = true, insertable = false, updatable = false)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "category_id", nullable = true, insertable = false, updatable = false)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "banned", nullable = false)
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(artifactId, artifact.artifactId) &&
                Objects.equals(name, artifact.name) &&
                Objects.equals(approved, artifact.approved) &&
                Objects.equals(description, artifact.description) &&
                Objects.equals(ageId, artifact.ageId) &&
                Arrays.equals(avatarSmall, artifact.avatarSmall) &&
                Arrays.equals(avatarFull, artifact.avatarFull) &&
                Objects.equals(owner, artifact.owner) &&
                Objects.equals(approver, artifact.approver) &&
                Objects.equals(origin, artifact.origin) &&
                Objects.equals(countryId, artifact.countryId) &&
                Objects.equals(categoryId, artifact.categoryId) &&
                Objects.equals(banned, artifact.banned);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(artifactId, name, approved, description, ageId, owner, approver, origin, countryId, categoryId, banned);
        result = 31 * result + Arrays.hashCode(avatarSmall);
        result = 31 * result + Arrays.hashCode(avatarFull);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "age_id", referencedColumnName = "age_id")
    public Age getAgeByAgeId() {
        return ageByAgeId;
    }

    public void setAgeByAgeId(Age ageByAgeId) {
        this.ageByAgeId = ageByAgeId;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByOwner() {
        return humanByOwner;
    }

    public void setHumanByOwner(Human humanByOwner) {
        this.humanByOwner = humanByOwner;
    }

    @ManyToOne
    @JoinColumn(name = "approver", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByApprover() {
        return humanByApprover;
    }

    public void setHumanByApprover(Human humanByApprover) {
        this.humanByApprover = humanByApprover;
    }

    @ManyToOne
    @JoinColumn(name = "origin", referencedColumnName = "country_id")
    public Country getCountryByOrigin() {
        return countryByOrigin;
    }

    public void setCountryByOrigin(Country countryByOrigin) {
        this.countryByOrigin = countryByOrigin;
    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @OneToOne(mappedBy = "artifactByArtifactId")
    public Auction getAuctionByArtifactId() {
        return auctionByArtifactId;
    }

    public void setAuctionByArtifactId(Auction auctionByArtifactId) {
        this.auctionByArtifactId = auctionByArtifactId;
    }

    @OneToOne(mappedBy = "artifactByArtifactId")
    public ExpeditionResult getExpeditionResultByArtifactId() {
        return expeditionResultByArtifactId;
    }

    public void setExpeditionResultByArtifactId(ExpeditionResult expeditionResultByArtifactId) {
        this.expeditionResultByArtifactId = expeditionResultByArtifactId;
    }
}
