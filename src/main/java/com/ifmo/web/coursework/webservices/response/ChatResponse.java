package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private Integer id;
    private String name, description;
//    private List<Integer> members;
//    private MessageResponse last_message;

    public static ChatResponse fromChat(Chat chat) {
        if (null == chat) return null;
        return builder().
                id(chat.getChatId())
                .name(chat.getName())
                .description(chat.getDescription())
//                .members(chat.getMembers()
//                        .stream()
//                        .map(HumanResponse::fromHuman)
//                        .map(HumanResponse::getId)
//                        .collect(Collectors.toList()))
//                .last_message(MessageResponse.fromMessage(
//                        chat.getMessagesByChatId().stream()
//                                .max(Comparator.comparing(Message::getDate))
//                                .orElse(null)))
                .build();
    }
}
