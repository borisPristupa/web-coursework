package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.ExpeditionStage;
import com.ifmo.web.coursework.data.repository.ExpeditionStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stage")
public class StageController {
    private final ExpeditionStageRepository expeditionStageRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getStagesNames() {
        return expeditionStageRepository.findAll().stream()
                .map(ExpeditionStage::getName)
                .collect(Collectors.toList());
    }

    @Autowired
    public StageController(ExpeditionStageRepository expeditionStageRepository) {
        this.expeditionStageRepository = expeditionStageRepository;
    }
}
