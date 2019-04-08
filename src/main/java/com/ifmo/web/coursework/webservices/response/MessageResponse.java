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
    private Integer id;
    private String text;
    private Timestamp date;
    private HumanResponse sender;
    public static MessageResponse fromMessage(Message message) {
        if (null == message) return null;
        return builder()
                .id(message.getMessageId())
                .text(message.getBody())
                .date(message.getDate())
                .sender(HumanResponse.fromHuman(message.getHumanByHumanId()))
                .build();
    }

}
