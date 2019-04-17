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

    @Query(nativeQuery = true, value = "INSERT INTO human_chat (chat_id, human_id) VALUES (?1, ?2)")
    void addMember(Integer chatId, Integer humanId);
    @Query(nativeQuery = true, value = "DELETE FROM human_chat WHERE chat_id=?1 AND human_id=?2")
    void removeMember(Integer chatId, Integer humanId);


    @Query("select a from Chat a join Message b on a.chatId = b.chatId  order by b.date")
    List<Chat> findAllByMessageOrderByDate();

}
