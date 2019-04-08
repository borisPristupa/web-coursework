package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {

    List<Chat> findAllByName(String name);

    @Query(value = "SELECT c.* FROM chat c " +
            "JOIN human_chat hc on c.chat_id = hc.chat_id " +
            "JOIN human h on hc.human_id = h.human_id " +
            "WHERE h.human_id = ?1 GROUP BY c.chat_id", nativeQuery = true)
    List<Chat> findAllByHumanId(Integer humanId);

    @Query("select a from Chat a join Message b on a.chatId = b.chatId  order by b.date")
    List<Chat> findAllByMessageOrderByDate();

}
