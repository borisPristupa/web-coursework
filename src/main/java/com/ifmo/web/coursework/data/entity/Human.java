package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Human {
    private Integer humanId;
    private String login;
    private String password;
    private String email;
    private String vkId;
    private String tgNickname;
    private String firstName;
    private String secondName;
    private String lastName;
    private String bio = "";
    private Integer likes = 0;
    private Integer dislikes = 0;
    private byte[] avatarSmall;
    private byte[] avatarFull;
    private Boolean banned = false;
    private Integer countryId;
    private Boolean archaeologist = false;
    private Boolean researcher = false;
    private Boolean collector = false;
    private Boolean sponsor = false;
    private Boolean moderator = false;
    private Collection<Artifact> artifactsByOwner;
    private Collection<Artifact> artifactsByApprover;
    private Collection<Auction> auctionsByRaiser;
    private Collection<Donation> donationsByHumanId;
    private Collection<ExpeditionResult> expeditionResultsByHumanId;
    private Collection<Expedition> expeditionsByHumanId;
    private Country countryByCountryId;
    private Collection<HumanChat> humanChatsByHumanId;
    private Collection<Message> messagesByHumanId;
    private Collection<ParticipationExpedition> participationExpeditionsByHumanId;
    private Collection<SubscriptionAuction> subscriptionAuctionsByHumanId;
    private Collection<SubscriptionExpedition> subscriptionExpeditionsByHumanId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "human_id", nullable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 255)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "vk_id", nullable = true, length = -1)
    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    @Basic
    @Column(name = "tg_nickname", nullable = true, length = -1)
    public String getTgNickname() {
        return tgNickname;
    }

    public void setTgNickname(String tgNickname) {
        this.tgNickname = tgNickname;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "second_name", nullable = false, length = 50)
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "bio", nullable = false, length = -1)
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Basic
    @Column(name = "archaeologist", nullable = false)
    public Boolean getArchaeologist() {
        return archaeologist;
    }

    public void setArchaeologist(Boolean archaeologist) {
        this.archaeologist = archaeologist;
    }

    @Basic
    @Column(name = "researcher", nullable = false)
    public Boolean getResearcher() {
        return researcher;
    }

    public void setResearcher(Boolean researcher) {
        this.researcher = researcher;
    }

    @Basic
    @Column(name = "collector", nullable = false)
    public Boolean getCollector() {
        return collector;
    }

    public void setCollector(Boolean collector) {
        this.collector = collector;
    }

    @Basic
    @Column(name = "sponsor", nullable = false)
    public Boolean getSponsor() {
        return sponsor;
    }

    public void setSponsor(Boolean sponsor) {
        this.sponsor = sponsor;
    }

    @Basic
    @Column(name = "moderator", nullable = false)
    public Boolean getModerator() {
        return moderator;
    }

    public void setModerator(Boolean moderator) {
        this.moderator = moderator;
    }

    @Basic
    @Column(name = "likes", nullable = false)
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Basic
    @Column(name = "dislikes", nullable = false)
    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
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
    @Column(name = "banned", nullable = false)
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Basic
    @Column(name = "country_id", nullable = true, updatable = false, insertable = false)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(humanId, human.humanId) &&
                Objects.equals(login, human.login) &&
                Objects.equals(password, human.password) &&
                Objects.equals(email, human.email) &&
                Objects.equals(vkId, human.vkId) &&
                Objects.equals(tgNickname, human.tgNickname) &&
                Objects.equals(firstName, human.firstName) &&
                Objects.equals(secondName, human.secondName) &&
                Objects.equals(lastName, human.lastName) &&
                Objects.equals(bio, human.bio) &&
                Objects.equals(likes, human.likes) &&
                Objects.equals(dislikes, human.dislikes) &&
                Arrays.equals(avatarSmall, human.avatarSmall) &&
                Arrays.equals(avatarFull, human.avatarFull) &&
                Objects.equals(banned, human.banned) &&
                Objects.equals(countryId, human.countryId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(humanId, login, password, email, vkId, tgNickname, firstName, secondName, lastName, bio, likes, dislikes, banned, countryId);
        result = 31 * result + Arrays.hashCode(avatarSmall);
        result = 31 * result + Arrays.hashCode(avatarFull);
        return result;
    }

    @OneToMany(mappedBy = "humanByOwner")
    public Collection<Artifact> getArtifactsByOwner() {
        return artifactsByOwner;
    }

    public void setArtifactsByOwner(Collection<Artifact> artifactsByHumanId) {
        this.artifactsByOwner = artifactsByHumanId;
    }

    @OneToMany(mappedBy = "humanByApprover")
    public Collection<Artifact> getArtifactsByApprover() {
        return artifactsByApprover;
    }

    public void setArtifactsByApprover(Collection<Artifact> artifactsByHumanId_0) {
        this.artifactsByApprover = artifactsByHumanId_0;
    }

    @OneToMany(mappedBy = "humanByRaiser")
    public Collection<Auction> getAuctionsByRaiser() {
        return auctionsByRaiser;
    }

    public void setAuctionsByRaiser(Collection<Auction> auctionsByHumanId) {
        this.auctionsByRaiser = auctionsByHumanId;
    }

    @OneToMany(mappedBy = "humanByDonator")
    public Collection<Donation> getDonationsByHumanId() {
        return donationsByHumanId;
    }

    public void setDonationsByHumanId(Collection<Donation> donationsByHumanId) {
        this.donationsByHumanId = donationsByHumanId;
    }

    @OneToMany(mappedBy = "resultFinderByHumanId")
    public Collection<ExpeditionResult> getExpeditionResultsByHumanId() {
        return expeditionResultsByHumanId;
    }

    public void setExpeditionResultsByHumanId(Collection<ExpeditionResult> expeditionResultsByHumanId) {
        this.expeditionResultsByHumanId = expeditionResultsByHumanId;
    }

    @OneToMany(mappedBy = "humanByHead")
    public Collection<Expedition> getExpeditionsByHumanId() {
        return expeditionsByHumanId;
    }

    public void setExpeditionsByHumanId(Collection<Expedition> expeditionsByHumanId) {
        this.expeditionsByHumanId = expeditionsByHumanId;
    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

    @OneToMany(mappedBy = "humanByHumanId")
    public Collection<HumanChat> getHumanChatsByHumanId() {
        return humanChatsByHumanId;
    }

    public void setHumanChatsByHumanId(Collection<HumanChat> humanChatsByHumanId) {
        this.humanChatsByHumanId = humanChatsByHumanId;
    }

    @OneToMany(mappedBy = "humanByHumanId")
    public Collection<Message> getMessagesByHumanId() {
        return messagesByHumanId;
    }

    public void setMessagesByHumanId(Collection<Message> messagesByHumanId) {
        this.messagesByHumanId = messagesByHumanId;
    }

    @OneToMany(mappedBy = "humanByHumanId")
    public Collection<ParticipationExpedition> getParticipationExpeditionsByHumanId() {
        return participationExpeditionsByHumanId;
    }

    public void setParticipationExpeditionsByHumanId(Collection<ParticipationExpedition> participationExpeditionsByHumanId) {
        this.participationExpeditionsByHumanId = participationExpeditionsByHumanId;
    }

    @OneToMany(mappedBy = "humanByHumanId")
    public Collection<SubscriptionAuction> getSubscriptionAuctionsByHumanId() {
        return subscriptionAuctionsByHumanId;
    }

    public void setSubscriptionAuctionsByHumanId(Collection<SubscriptionAuction> subscriptionAuctionsByHumanId) {
        this.subscriptionAuctionsByHumanId = subscriptionAuctionsByHumanId;
    }

    @OneToMany(mappedBy = "humanByHumanId")
    public Collection<SubscriptionExpedition> getSubscriptionExpeditionsByHumanId() {
        return subscriptionExpeditionsByHumanId;
    }

    public void setSubscriptionExpeditionsByHumanId(Collection<SubscriptionExpedition> subscriptionExpeditionsByHumanId) {
        this.subscriptionExpeditionsByHumanId = subscriptionExpeditionsByHumanId;
    }
}
