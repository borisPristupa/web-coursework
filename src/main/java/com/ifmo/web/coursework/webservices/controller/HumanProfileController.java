package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.repository.HumanRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.exception.HumanNotFoundException;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/human")
public class HumanProfileController {
    private final HumanRepository humanRepository;
    private final HumanUtils humanUtils;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse getProfile(@RequestParam(value = "id", required = false) Integer id) {
        if (null == id) id = humanUtils.getCurrentId();

        Integer finalId = id;
        return HumanResponse.fromHuman(humanRepository.findById(id)
                .orElseThrow(() ->
                        new HumanNotFoundException("No user with id '" + finalId + "' found in DB")));
    }

    @Autowired
    public HumanProfileController(HumanRepository humanRepository, HumanUtils humanUtils) {
        this.humanRepository = humanRepository;
        this.humanUtils = humanUtils;
    }
}
