package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Chat;
import com.ifmo.web.coursework.data.repository.ChatRepository;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import com.ifmo.web.coursework.data.utils.FilterUtils;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final HumanUtils humanUtils;
    private final ChatRepository chatRepository;
    private final HumanRepository humanRepository;
    private final FilterUtils filterUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    public ChatResponse getChat(@RequestParam("id") Integer id) {
        Chat chat = chatRepository.getOne(id);
        chat.setMembers(humanRepository.findAllByChatId(chat.getChatId()));
        return ChatResponse.fromChat(chat);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ChatResponse> search(@RequestParam(value = "searchfor", required = false, defaultValue = "") String pattern) {
        return humanUtils.getCurrentHuman().getChats().stream()
                .filter(filterUtils.nameFilter(pattern, Chat::getName))
                .peek(chat -> chat.setMembers(humanRepository.findAllByChatId(chat.getChatId())))
                .map(ChatResponse::fromChat)
                .filter(Objects::nonNull)
                .sorted(Comparator
                        .<ChatResponse, Timestamp>comparing(chat -> chat.getLast_message().getDate())
                        .reversed())
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/edit")
    public ChatResponse editChat(ChatResponse chatResponse) {
        if (null == chatResponse.getId()) throw new MissingRequiredArgumentException("id");

        Chat chat = chatRepository.findById(chatResponse.getId()).orElseThrow(() ->
                new NotFoundException("Chat not found by id " + chatResponse.getId()));

        if (null != chatResponse.getDescription())
            chat.setDescription(chatResponse.getDescription());

        if (null != chatResponse.getName())
            chat.setName(chatResponse.getName());

//        if (null != chatResponse.getMembers())
//            chat.set

        chatRepository.save(chat);
        chat.setMembers(humanRepository.findAllByChatId(chat.getChatId()));
        return ChatResponse.fromChat(chat);
    }

    @Autowired
    public ChatController(HumanUtils humanUtils, ChatRepository chatRepository, HumanRepository humanRepository, FilterUtils filterUtils) {
        this.humanUtils = humanUtils;
        this.chatRepository = chatRepository;
        this.humanRepository = humanRepository;
        this.filterUtils = filterUtils;
    }
}
