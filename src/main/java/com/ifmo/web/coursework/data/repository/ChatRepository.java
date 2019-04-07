package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {

    List<Chat> findAllByName(String name);

    @Query("select a from Chat a join HumanChat b on a.chatId = b.chatId where b.humanId = :humanid")
    List<Chat> findAllByHumanId(@Param("humanid") Integer humanId);

    @Query("select a from Chat a join Message b on a.chatId = b.chatId  order by b.date")
    List<Chat> findAllByMessageOrderByDate();
}
