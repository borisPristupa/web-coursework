package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Integer id, chat_id;
    private String text;
    private Timestamp date;
    private Integer sender_id;
    public static MessageResponse fromMessage(Message message) {
        if (null == message) return null;
        return builder()
                .id(message.getMessageId())
                .chat_id(message.getChatId())
                .text(message.getBody())
                .date(message.getDate())
                .sender_id(message.getHumanId())
                .build();
    }

}
