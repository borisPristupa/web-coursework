package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.HumanChat;
import com.ifmo.web.coursework.data.entity.HumanChatPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanChatRepository extends JpaRepository<HumanChat,HumanChatPK> {
}
