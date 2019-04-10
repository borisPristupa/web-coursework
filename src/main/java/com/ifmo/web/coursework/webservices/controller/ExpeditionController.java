package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Expedition;
import com.ifmo.web.coursework.data.repository.ExpeditionRepository;
import com.ifmo.web.coursework.data.repository.ExpeditionStageRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.ExpeditionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expedition") // TODO: 10.04.19 TEST
// TODO: 10.04.19 GET STAGES
public class ExpeditionController {
    private final ExpeditionRepository expeditionRepository;
    private final ExpeditionStageRepository stageRepository;
    private final HumanUtils humanUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse get(@RequestParam("id") Integer id) {
        return ExpeditionResponse.fromExpedition(expeditionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + id + "'")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpeditionResponse add(ExpeditionResponse expeditionResponse) {
        ArrayList<String> missing = new ArrayList<>();
        if (null == expeditionResponse.getName())
            missing.add("name");
        if (null == expeditionResponse.getStage())
            missing.add("stage");
        if (null == expeditionResponse.getFull_sum())
            missing.add("full_sum");

        if (!missing.isEmpty())
            throw new MissingRequiredArgumentException(missing.toArray(new String[0]));

        Expedition created = new Expedition();

        created.setExpeditionStageByStageId(stageRepository.findByName(expeditionResponse.getStage())
                .orElseThrow(() ->
                        new NotFoundException("Not found expedition stage '" + expeditionResponse.getStage() + "'")));
        created.setBanned(false);
        created.setName(expeditionResponse.getName());
        created.setCosts(expeditionResponse.getFull_sum());
        created.setCurrentSum(0);
        created.setDescription(expeditionResponse.getDescription());
        created.setHumanByHead(humanUtils.getCurrentHuman());

        expeditionRepository.save(created);

        return ExpeditionResponse.fromExpedition(
                expeditionRepository.findOne(Example.of(created))
                        .orElseGet(() ->
                                expeditionRepository.findFirstByOrderByExpeditionIdDesc()
                                        .orElseThrow(() ->
                                                new IllegalStateException("Could not save a new expedition '" +
                                                        created.getName() + "'")))
        );
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ExpeditionResponse> search(@RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam("stages[]") List<String> stages,
                                           @RequestParam("members_max") Integer membersMax,
                                           @RequestParam("members_min") Integer membersMin,
                                           @RequestParam("full_sum_max") Integer fullSumMax,
                                           @RequestParam("full_sum_min") Integer fullSumMin) {
        return expeditionRepository.findAllByCostsBetween(fullSumMin, fullSumMax, PageRequest.of(page, amount)).stream()
                .filter(expedition -> expedition.getParticipationExpeditionsByExpeditionId().size() >= membersMin)
                .filter(expedition -> expedition.getParticipationExpeditionsByExpeditionId().size() <= membersMax)
                .map(ExpeditionResponse::fromExpedition)
                .filter(expeditionResponse -> stages.isEmpty() || stages.contains(expeditionResponse.getStage()))
                .collect(Collectors.toList());
    }


    @Autowired
    public ExpeditionController(ExpeditionRepository expeditionRepository, ExpeditionStageRepository stageRepository, HumanUtils humanUtils) {
        this.expeditionRepository = expeditionRepository;
        this.stageRepository = stageRepository;
        this.humanUtils = humanUtils;
    }
}
