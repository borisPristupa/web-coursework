package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    Optional<Message> findByMessageId(Integer message_id);
    Optional<Message> findByChatId(Integer chat_id);
    Optional<Message> findByHumanId(Integer human_id);
    List<Message> findAllByBody(String body);
    List<Message> findAllByDate(Date date);

    List<Message> findAllByChatIdOrderByDateDesc(Integer chat_id, Pageable pageable);
}
