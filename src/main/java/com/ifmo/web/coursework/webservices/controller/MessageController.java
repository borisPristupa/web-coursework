package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Message;
import com.ifmo.web.coursework.data.repository.MessageRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import com.ifmo.web.coursework.webservices.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final HumanUtils humanUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MessageResponse> getMessages(@RequestParam("chat_id") int chatId,
                                             @RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return messageRepository.findAllByChatIdOrderByDateDesc(chatId, PageRequest.of(page, amount)).stream()
                .map(MessageResponse::fromMessage)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public MessageResponse addMessage(MessageResponse messageResponse) {
        messageResponse.setDate(Timestamp.valueOf(LocalDateTime.now()));
        messageResponse.setSender(HumanResponse.fromHuman(humanUtils.getCurrentHuman()));

        Message message = new Message();
        message.setBody(messageResponse.getText());
        message.setChatId(messageResponse.getChat_id());
        message.setHumanId(messageResponse.getSender().getId());
        message.setDate(messageResponse.getDate());
        messageRepository.save(message);

        return messageResponse;
    }

    @Autowired
    public MessageController(MessageRepository messageRepository, HumanUtils humanUtils) {
        this.messageRepository = messageRepository;
        this.humanUtils = humanUtils;
    }
}
