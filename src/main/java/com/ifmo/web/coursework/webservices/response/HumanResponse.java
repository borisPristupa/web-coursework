package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Human;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HumanResponse {
    private Integer id;
    private String login;
    private String first_name, second_name, last_name = "";
    private String bio = "";
    private String vk_id, tg_nickname, email;
    private int likes = 0, dislikes = 0;
    private boolean banned = false;
    private CountryResponse country;
    private boolean moderator, researcher, archaeologist, collector, sponsor;

    public static HumanResponse fromHuman(Human human) {
        return HumanResponse.builder()
                .id(human.getHumanId())
                .login(human.getLogin())
                .first_name(human.getFirstName())
                .second_name(human.getSecondName())
                .last_name(human.getLastName())
                .vk_id(human.getVkId())
                .tg_nickname(human.getTgNickname())
                .email(human.getEmail())

                .bio(human.getBio())
                .likes(human.getLikes())
                .dislikes(human.getDislikes())
                .banned(human.getBanned())
                .country(CountryResponse.fromCountry(human.getCountryByCountryId()))

                .moderator(null != human.getModeratorByHumanId())
                .researcher(null != human.getResearcherByHumanId())
                .archaeologist(null != human.getArchaeologistByHumanId())
                .collector(null != human.getCollectorByHumanId())
                .sponsor(null != human.getSponsorByHumanId())
                .build();
    }
}
