package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Human;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HumanResponse {
    private int id;
    private String login;
    private String firstName, secondName, lastName;
    private String bio;
    private Integer likes, dislikes;

    public static HumanResponse fromHuman(Human human) {
        return HumanResponse.builder()
                .id(human.getHumanId())
                .login(human.getLogin())
                .bio(human.getBio())
                .firstName(human.getFirstName())
                .secondName(human.getSecondName())
                .lastName(human.getLastName())
                .likes(human.getLikes())
                .dislikes(human.getDislikes())
                .build();
    }
}
