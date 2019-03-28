package com.ifmo.web.coursework.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Chat {
    private Integer chatId;
    private String name;
    private String description;
    private byte[] avatarSmall;
    private byte[] avatarFull;
    private Integer memberNum;
    private Collection<HumanChat> humanChatsByChatId;
    private Collection<Message> messagesByChatId;

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
    @Column(name = "member_num", nullable = true)
    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(chatId, chat.chatId) &&
                Objects.equals(name, chat.name) &&
                Objects.equals(description, chat.description) &&
                Arrays.equals(avatarSmall, chat.avatarSmall) &&
                Arrays.equals(avatarFull, chat.avatarFull) &&
                Objects.equals(memberNum, chat.memberNum);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(chatId, name, description, memberNum);
        result = 31 * result + Arrays.hashCode(avatarSmall);
        result = 31 * result + Arrays.hashCode(avatarFull);
        return result;
    }

    @OneToMany(mappedBy = "chatByChatId")
    public Collection<HumanChat> getHumanChatsByChatId() {
        return humanChatsByChatId;
    }

    public void setHumanChatsByChatId(Collection<HumanChat> humanChatsByChatId) {
        this.humanChatsByChatId = humanChatsByChatId;
    }

    @OneToMany(mappedBy = "chatByChatId")
    public Collection<Message> getMessagesByChatId() {
        return messagesByChatId;
    }

    public void setMessagesByChatId(Collection<Message> messagesByChatId) {
        this.messagesByChatId = messagesByChatId;
    }
}
