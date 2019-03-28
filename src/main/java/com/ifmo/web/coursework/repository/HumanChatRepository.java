package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.HumanChat;
import com.ifmo.web.coursework.entity.HumanChatPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanChatRepository extends JpaRepository<HumanChat,HumanChatPK> {
}
