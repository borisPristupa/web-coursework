package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Moderator {
    private Integer humanId;
    private String tgNickname;
    private Human humanByHumanId;

    @Id
    @Column(name = "human_id", nullable = false)
    public Integer getHumanId() {
        return humanId;
    }

    public void setHumanId(Integer humanId) {
        this.humanId = humanId;
    }

    @Basic
    @Column(name = "tg_nickname", nullable = false, length = -1)
    public String getTgNickname() {
        return tgNickname;
    }

    public void setTgNickname(String tgNickname) {
        this.tgNickname = tgNickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moderator moderator = (Moderator) o;
        return Objects.equals(humanId, moderator.humanId) &&
                Objects.equals(tgNickname, moderator.tgNickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanId, tgNickname);
    }

    @OneToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }
}
