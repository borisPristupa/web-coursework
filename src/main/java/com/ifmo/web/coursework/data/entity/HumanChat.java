package com.ifmo.web.coursework.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "human_chat", schema = "public", catalog = "postgres")
@IdClass(HumanChatPK.class)
public class HumanChat {
    private Integer chatId;
    private Integer humanId;
    private Chat chatByChatId;
    private Human humanByHumanId;

    @Id
    @Column(name = "chat_id", nullable = false)
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    @Id
    @Column(name = "human_id", nullable = false)
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
        HumanChat humanChat = (HumanChat) o;
        return Objects.equals(chatId, humanChat.chatId) &&
                Objects.equals(humanId, humanChat.humanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, humanId);
    }

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", nullable = false)
    public Chat getChatByChatId() {
        return chatByChatId;
    }

    public void setChatByChatId(Chat chatByChatId) {
        this.chatByChatId = chatByChatId;
    }

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "human_id", nullable = false)
    public Human getHumanByHumanId() {
        return humanByHumanId;
    }

    public void setHumanByHumanId(Human humanByHumanId) {
        this.humanByHumanId = humanByHumanId;
    }
}
