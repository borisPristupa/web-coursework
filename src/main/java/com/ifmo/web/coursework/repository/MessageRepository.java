package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    Optional<Message> findByMessageId(Integer message_id);
    Optional<Message> findByChatId(Integer chat_id);
    Optional<Message> findByHumanId(Integer human_id);
    List<Message> findAllByBody(String body);
    List<Message> findAllByDate(Date date);

    @Query("select a from Message a where a.chatId = :chat_id order by a.date")
    List<Message> findAllOrderByDate(@Param("chat_id") Integer chat_id);
}
