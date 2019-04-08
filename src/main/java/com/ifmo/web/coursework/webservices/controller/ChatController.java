package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Chat;
import com.ifmo.web.coursework.data.repository.ChatRepository;
import com.ifmo.web.coursework.data.utils.FilterUtils;
import com.ifmo.web.coursework.data.utils.HumanUtils;
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
    private final FilterUtils filterUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ChatResponse> search(@RequestParam(value = "searchfor", required = false, defaultValue = "") String pattern) {
        return chatRepository.findAllByHumanId(humanUtils.getCurrentId()).stream()
                .filter(filterUtils.nameFilter(pattern, Chat::getName))
                .map(ChatResponse::fromChat)
                .filter(Objects::nonNull)
                .sorted(Comparator
                        .<ChatResponse, Timestamp>comparing(chat -> chat.getLast_message().getDate())
                        .reversed())
                .collect(Collectors.toList());
    }

    @Autowired
    public ChatController(HumanUtils humanUtils, ChatRepository chatRepository, FilterUtils filterUtils) {
        this.humanUtils = humanUtils;
        this.chatRepository = chatRepository;
        this.filterUtils = filterUtils;
    }
}
