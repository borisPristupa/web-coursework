package com.ifmo.web.coursework.data.entity;

import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Chat {
    private Integer chatId;
    private String name;
    private String description;
    private Collection<Message> messagesByChatId;

    @Setter
    private List<Human> members = Collections.emptyList();

    @Transient
    public List<Human> getMembers() {
        return members;
    }

    @Id
    @Column(name = "chat_id", nullable = false)
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(chatId, chat.chatId) &&
                Objects.equals(name, chat.name) &&
                Objects.equals(description, chat.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, description);
    }

    @OneToMany(mappedBy = "chatByChatId")
    public Collection<Message> getMessagesByChatId() {
        return messagesByChatId;
    }

    public void setMessagesByChatId(Collection<Message> messagesByChatId) {
        this.messagesByChatId = messagesByChatId;
    }

}
