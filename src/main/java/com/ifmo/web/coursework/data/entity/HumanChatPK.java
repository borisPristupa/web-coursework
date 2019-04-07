package com.ifmo.web.coursework.data.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class HumanChatPK implements Serializable {
    private Integer chatId;
    private Integer humanId;

    @Column(name = "chat_id", nullable = false, updatable = false, insertable = false)
    @Id
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    @Column(name = "human_id", nullable = false, updatable = false, insertable = false)
    @Id
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
        HumanChatPK that = (HumanChatPK) o;
        return Objects.equals(chatId, that.chatId) &&
                Objects.equals(humanId, that.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, humanId);
    }
}
