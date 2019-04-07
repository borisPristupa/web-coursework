package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HumanRepository extends JpaRepository<Human, Integer> {
    Optional<Human> findByLogin(String login);
    Optional<Human> findByVkId(String vkId);
    Optional<Human> findByEmail(String email);
    Optional<Human> findByTgNickname(String tg_nickname);
    List<Human> findAllByFirstName(String firstName);
    List<Human> findAllBySecondName(String secondName);
    List<Human> findAllByLastName(String last_name);
    List<Human> findAllByFirstNameAndSecondName(String firstName, String secondName);
    List<Human> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Human> findAllBySecondNameAndLastName(String second_name,String last_name);
    List<Human> findAllByFirstNameAndSecondNameAndLastName(String firstName, String secondName, String lastName);
    List<Human> findAllByBanned(Boolean banned);
    List<Human> findAllByCountryId(Integer countryId);

    @Query("select a from Human a join SubscriptionExpedition b on a.humanId = b.humanId WHERE b.expeditionId = :expedition")
    List<Human> findAllBySubscriptionExpedition(@Param("expedition") Integer expeditionId);

    @Query("select a from Human a join SubscriptionAuction b on a.humanId = b.humanId WHERE b.auctionId = :auction")
    List<Human> findAllBySubscriptionAuction(@Param("auction") Integer auctionId);

    @Query("select a from Human a join HumanChat b on a.humanId = b.humanId WHERE b.chatId = :chat")
    List<Human> findAllByHumanChat(@Param("chat") Integer chatId);

    @Query("select a from Human a order by ((a.likes + 1)/(a.likes+a.dislikes + 2)) DESC, a.likes DESC")
    List<Human> findAllByPopularity();
}
