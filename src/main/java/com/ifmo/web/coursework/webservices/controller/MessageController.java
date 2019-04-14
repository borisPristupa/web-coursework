package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Chat;
import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.entity.Message;
import com.ifmo.web.coursework.data.repository.ChatRepository;
import com.ifmo.web.coursework.data.repository.MessageRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.log.Log;
import com.ifmo.web.coursework.notification.jms.CustomJMSSender;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import com.ifmo.web.coursework.webservices.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final HumanUtils humanUtils;
    private final ChatRepository chatRepository;

    private final CustomJMSSender jms;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MessageResponse> getMessages(@RequestParam("chat_id") int chatId,
                                             @RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return messageRepository.findAllByChatIdOrderByDateDesc(chatId, PageRequest.of(page, amount)).stream()
                .map(MessageResponse::fromMessage)
                .collect(Collectors.toList());
    }

    @Log
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MessageResponse addMessage(MessageResponse messageResponse) {
        ArrayList<String> missing = new ArrayList<>();
        if (null == messageResponse.getChat_id())
            missing.add("chat_id");
        if (null == messageResponse.getSender())
            missing.add("sender");
        if (null == messageResponse.getText())
            missing.add("text");

        if (!missing.isEmpty())
            throw new MissingRequiredArgumentException(missing.toArray(new String[0]));

        Chat chat = chatRepository.findById(messageResponse.getChat_id()).orElseThrow(() ->
                new NotFoundException("Chat not found by id '" + messageResponse.getChat_id() + "'"));

        messageResponse.setDate(Timestamp.valueOf(LocalDateTime.now()));
        messageResponse.setSender(HumanResponse.fromHuman(humanUtils.getCurrentHuman()));

        Message message = new Message();
        message.setBody(messageResponse.getText());
        message.setChatId(messageResponse.getChat_id());
        message.setHumanId(messageResponse.getSender().getId());
        message.setDate(messageResponse.getDate());
        messageRepository.save(message);

        chat.getMembers().stream()
                .filter(human -> !human.getHumanId().equals(message.getHumanId()))
                .map(Human::getEmail)
                .forEach(email -> jms.send(CustomJMSSender.MAIL,
                        com.ifmo.web.coursework.notification.Message.builder()
                                .to(email)
                                .subject("Chat " + chat.getName())
                                .text("%s sent a message at chat '%s'! \n" +
                                        "Don't miss important news about archaeology\n" +
                                        "Why not?\n" +
                                        "Just don't")
                                .build()));

        return messageResponse;
    }

    @Autowired
    public MessageController(MessageRepository messageRepository, HumanUtils humanUtils, ChatRepository chatRepository, CustomJMSSender jms) {
        this.messageRepository = messageRepository;
        this.humanUtils = humanUtils;
        this.chatRepository = chatRepository;
        this.jms = jms;
    }
}
