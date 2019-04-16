package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Chat;
import com.ifmo.web.coursework.data.repository.ChatRepository;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import com.ifmo.web.coursework.data.utils.FilterUtils;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.log.Log;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final HumanUtils humanUtils;
    private final ChatRepository chatRepository;
    private final HumanRepository humanRepository;
    private final FilterUtils filterUtils;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ChatResponse getChat(@RequestParam("id") Integer id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Chat not found by id " + id));
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
//                .sorted(Comparator
//                        .<ChatResponse, Timestamp>comparing(chat -> chat.getLast_message().getDate())
//                        .reversed())
                .collect(Collectors.toList());
    }

    @Log
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ChatResponse addChat(ChatResponse chatResponse,
                                @RequestParam("members[]") List<Integer> members) {
        ArrayList<String> missing = new ArrayList<>();
        if (null == chatResponse.getName())
            missing.add("name");
//        if (null == chatResponse.getMembers())
//            missing.add("members");

        if (!missing.isEmpty())
            throw new MissingRequiredArgumentException(missing.toArray(new String[0]));

        members.add(humanUtils.getCurrentId());

//        chatResponse.getMembers().add(humanUtils.getCurrentId());

        Chat chat = new Chat();
//        chat.setMembers(chatResponse.getMembers().stream()
        chat.setMembers(members.stream()
                .map(humanRepository::getOne)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
//        chat.setMembers(
//                Stream.of(1, humanUtils.getCurrentId())
//                        .map(humanRepository::getOne)
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.toList()));
        chat.setName(chatResponse.getName());
        chat.setDescription(Optional.ofNullable(chatResponse.getDescription()).orElse(""));
        chatRepository.save(chat);


//        Stream.of(1, humanUtils.getCurrentId())
//                .map(humanRepository::getOne)
//                .filter(Objects::nonNull)
//                .forEach(human -> {
//                    human.getChats().add(chatRepository.findOne(Example.of(chat)).orElse(chat));
//                    humanRepository.save(human);
//                });
        members.stream()
                .map(humanRepository::getOne)
                .filter(Objects::nonNull)
                .forEach(human -> {
                    human.getChats().add(chatRepository.findOne(Example.of(chat)).orElse(chat));
                    humanRepository.save(human);
                });

        return ChatResponse.fromChat(chat);
    }

    @Log
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public ChatResponse editChat(ChatResponse chatResponse) {
        if (null == chatResponse.getId()) throw new MissingRequiredArgumentException("id");

        Chat chat = chatRepository.findById(chatResponse.getId()).orElseThrow(() ->
                new NotFoundException("Chat not found by id " + chatResponse.getId()));

        if (null != chatResponse.getDescription())
            chat.setDescription(chatResponse.getDescription());

        if (null != chatResponse.getName())
            chat.setName(chatResponse.getName());

//        if (null != chatResponse.getMembers()) {
//            chatResponse.getMembers().forEach(humanResponse -> {
//                if (chat.getMembers().stream().noneMatch(human -> human.getHumanId().equals(humanResponse)))
//                    chatRepository.addMember(chat.getChatId(), humanResponse);
//            });
//            chat.getMembers().forEach(human -> {
//                if (chatResponse.getMembers().stream().noneMatch(humanResponse -> human.getHumanId().equals(humanResponse)))
//                    chatRepository.removeMember(chat.getChatId(), human.getHumanId());
//            });
//        }

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
