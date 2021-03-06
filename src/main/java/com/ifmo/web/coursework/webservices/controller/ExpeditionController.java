package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.*;
import com.ifmo.web.coursework.data.repository.*;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.log.Log;
import com.ifmo.web.coursework.notification.Message;
import com.ifmo.web.coursework.notification.jms.CustomJMSSender;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.DonationResponse;
import com.ifmo.web.coursework.webservices.response.ExpeditionResponse;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import com.ifmo.web.coursework.webservices.response.SuccessResponse;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log
@RestController
@ToString
@RequestMapping("/expedition")
public class ExpeditionController {
    private final ExpeditionRepository expeditionRepository;
    private final ExpeditionStageRepository stageRepository;
    private final SubscriptionExpeditionRepository subscriptionRepository;
    private final ParticipationExpeditionRepository participationRepository;
    private final HumanRepository humanRepository;
    private final HumanUtils humanUtils;

    private final CustomJMSSender jms;

    @Log.Exclude
    private void notify(String expeditionName, String text, List<Human> humans) {
        humans.stream()
                .map(Human::getEmail)
                .forEach(email -> jms.send(CustomJMSSender.MAIL, Message.builder()
                        .to(email)
                        .subject(expeditionName + " expedition")
                        .text(text)
                        .build()));
    }

    @Log.Exclude
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse get(@RequestParam("id") Integer id) {
        return ExpeditionResponse.fromExpedition(expeditionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + id + "'")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpeditionResponse add(ExpeditionResponse expeditionResponse,
                                  @RequestParam("members_id[]") List<Integer> members) {
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


        try {
            expeditionRepository.save(created);

            members.add(humanUtils.getCurrentId());
            members.stream()
                    .map(humanRepository::getOne)
                    .filter(Objects::nonNull)
                    .forEach(human -> {
                        ParticipationExpedition pe = new ParticipationExpedition();
                        pe.setDate(Date.valueOf(LocalDate.now()));
                        pe.setExpeditionByExpeditionId(
                                expeditionRepository.findOne(Example.of(created)).orElse(created));
                        pe.setHumanByHumanId(human);
                        participationRepository.save(pe);
                    });
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(ExpeditionController.class);
            logger.error("WTF", e);
            throw e;
        }

        return ExpeditionResponse.fromExpedition(
                expeditionRepository.findOne(Example.of(created))
                        .orElseGet(() ->
                                expeditionRepository.findFirstByOrderByExpeditionIdDesc()
                                        .orElseThrow(() ->
                                                new IllegalStateException("Could not save a new expedition '" +
                                                        created.getName() + "'")))
        );
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse edit(ExpeditionResponse expeditionResponse) {
        if (null == expeditionResponse.getId()) throw new MissingRequiredArgumentException("id");

        Expedition expedition = expeditionRepository.findById(expeditionResponse.getId()).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionResponse.getId() + "'"));
        String oldName = expedition.getName();

        if (null != expeditionResponse.getName())
            expedition.setName(expeditionResponse.getName());

        if (null != expeditionResponse.getDescription())
            expedition.setDescription(expeditionResponse.getDescription());

        if (null != expeditionResponse.getStage())
            expedition.setExpeditionStageByStageId(stageRepository.findByName(expeditionResponse.getStage())
                    .orElseThrow(() ->
                            new NotFoundException("Stage of expedition not found: '" + expeditionResponse.getStage() + "'")));

        if (null != expeditionResponse.getFull_sum())
            expedition.setCosts(expeditionResponse.getCurrent_sum());

        expeditionRepository.save(expedition);

        notify(oldName,
                "The expedition '" + oldName + "' has been updated", expedition.getSubscriptionExpeditionsByExpeditionId().stream()
                        .map(SubscriptionExpedition::getHumanByHumanId)
                        .collect(Collectors.toList())
        );
        return ExpeditionResponse.fromExpedition(expedition);
    }

    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public List<HumanResponse> getMembers(@RequestParam("expedition_id") Integer expeditionId) {
        return expeditionRepository.findById(expeditionId).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionId + "'"))
                .getParticipationExpeditionsByExpeditionId()
                .stream()
                .map(ParticipationExpedition::getHumanByHumanId)
                .map(HumanResponse::fromHuman)
                .collect(Collectors.toList());
    }

    @Log.Exclude
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ExpeditionResponse> search(@RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam("stages[]") List<String> stages,
                                           @RequestParam("members_max") Integer membersMax,
                                           @RequestParam("members_min") Integer membersMin,
                                           @RequestParam("full_sum_max") Integer fullSumMax,
                                           @RequestParam("full_sum_min") Integer fullSumMin) {
        return expeditionRepository.findAllByCostsBetweenAndBannedIsFalse(fullSumMin, fullSumMax, PageRequest.of(page, amount)).stream()
                .filter(expedition -> expedition.getParticipationExpeditionsByExpeditionId().size() >= membersMin)
                .filter(expedition -> expedition.getParticipationExpeditionsByExpeditionId().size() <= membersMax)
                .map(ExpeditionResponse::fromExpedition)
                .filter(expeditionResponse -> stages.isEmpty() || stages.contains(expeditionResponse.getStage()))
                .collect(Collectors.toList());
    }

    @PatchMapping("/privileged/ban")
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse ban(@RequestParam("id") Integer id,
                                  @RequestParam("banned") Boolean banned) {
        Expedition expedition = expeditionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + id + "'"));
        expedition.setBanned(banned);
        expeditionRepository.save(expedition);

        notify(expedition.getName(), "The expedition '" + expedition.getName() + "' has been " +
                        (banned ? "" : "un") + "banned",
                subscriptionRepository.findAllByExpeditionId(id).stream()
                        .map(SubscriptionExpedition::getHumanByHumanId)
                        .collect(Collectors.toList()));
        return ExpeditionResponse.fromExpedition(expedition);
    }

    @PatchMapping("/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse subscribe(@RequestParam("expedition_id") Integer expeditionId,
                                     @RequestParam(value = "subscribed", required = false, defaultValue = "true") Boolean subscribed) {
        Expedition expedition = expeditionRepository.findById(expeditionId).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionId + "'"));

        SubscriptionExpeditionPK pk = new SubscriptionExpeditionPK();
        pk.setExpeditionId(expeditionId);
        pk.setHumanId(humanUtils.getCurrentId());

        if (subscribed) {
            if (subscriptionRepository.existsById(pk)) return new SuccessResponse("Already subscribed");

            SubscriptionExpedition subscriptionExpedition = new SubscriptionExpedition();
            subscriptionExpedition.setExpeditionId(expedition.getExpeditionId());
            subscriptionExpedition.setHumanId(humanUtils.getCurrentId());
            subscriptionRepository.save(subscriptionExpedition);

            notify(expedition.getName(), "You are now subscribed to the '" + expedition.getName() + "' expedition's news",
                    Collections.singletonList(humanUtils.getCurrentHuman()));

            return new SuccessResponse("Subscribed");
        } else {
            Optional<SubscriptionExpedition> byId = subscriptionRepository.findById(pk);

            if (byId.isPresent()) {
                subscriptionRepository.delete(byId.get());
                return new SuccessResponse("Unsubscribed");
            }

            notify(expedition.getName(), "You are no more subscribed to the '" + expedition.getName() + "' expedition's news",
                    Collections.singletonList(humanUtils.getCurrentHuman()));

            return new SuccessResponse("Not subscribed yet");
        }
    }


    @PostMapping("/donate")
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse donate(@RequestParam("expedition_id") Integer expeditionId,
                                     @RequestParam("value") Integer value) {
        Expedition expedition = expeditionRepository.findById(expeditionId).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionId + "'"));

        expedition.setCurrentSum(expedition.getCurrentSum() + value);
        expeditionRepository.save(expedition);

        return ExpeditionResponse.fromExpedition(expedition);
    }

    @PatchMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public ExpeditionResponse addMember(@RequestParam("expedition_id") Integer expeditionId,
                                        @RequestParam("human_id") Integer humanId,
                                        @RequestParam("add") Boolean add) {
        Expedition expedition = expeditionRepository.findById(expeditionId).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionId + "'"));


        Human human = humanRepository.findById(humanId).orElseThrow(() ->
                new NotFoundException("Human not found by id '" + humanId + "'"));

        if (add && expedition.getParticipationExpeditionsByExpeditionId().stream()
                .map(ParticipationExpedition::getHumanId)
                .noneMatch(humanId::equals)) {
            ParticipationExpedition pe = new ParticipationExpedition();
            pe.setHumanByHumanId(human);
            pe.setExpeditionByExpeditionId(expedition);
            pe.setDate(Date.valueOf(LocalDate.now()));
            participationRepository.save(pe);

            pe = participationRepository.findOne(Example.of(pe)).orElseThrow(
                    () -> new IllegalStateException("Failed to add member")
            );

            expedition.getParticipationExpeditionsByExpeditionId().add(pe);
            expeditionRepository.save(expedition);

            notify(expedition.getName(), "You are now a member of the expedition '" + expedition.getName() + "'",
                    Collections.singletonList(human));

            return ExpeditionResponse.fromExpedition(expedition);
        } else if (!add && expedition.getParticipationExpeditionsByExpeditionId().stream()
                .map(ParticipationExpedition::getHumanId)
                .anyMatch(humanId::equals)) {

            expedition.getParticipationExpeditionsByExpeditionId().stream()
                    .filter(hid -> hid.getHumanId().equals(humanId))
                    .forEach(participationExpedition -> {
                        notify(expedition.getName(), "You have been excluded from the expedition '" + expedition.getName() + "'",
                                Collections.singletonList(human));

                        participationRepository.deleteById(participationExpedition.getParticipationExpeditionId());
                        expedition.getParticipationExpeditionsByExpeditionId().remove(participationExpedition);
                    });
            return ExpeditionResponse.fromExpedition(expeditionRepository.findById(expeditionId).orElseThrow(() ->
                    new IllegalStateException("Something went wrong while deleting member of an expedition")));
        }

        return ExpeditionResponse.fromExpedition(expedition);
    }

    @Log.Exclude
    @GetMapping("/donations")
    @ResponseStatus(HttpStatus.OK)
    public List<DonationResponse> getDonations(@RequestParam("expedition_id") Integer expeditionId) {
        List<DonationResponse> collect = expeditionRepository.findById(expeditionId).orElseThrow(() ->
                new NotFoundException("Expedition not found by id '" + expeditionId + "'"))
                .getDonationsByExpeditionId()
                .stream()
                .map(DonationResponse::fromDonation)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(dr -> dr.getDonator().getId())).collect(Collectors.toList());

        int prevId = -1;
        LinkedList<DonationResponse> result = new LinkedList<>();
        for (DonationResponse donationResponse : collect)
            if (donationResponse.getDonator().getId() != prevId) {
                prevId = donationResponse.getDonator().getId();
                result.addLast(donationResponse);
            } else {
                result.getLast().setAmount(result.getLast().getAmount() + donationResponse.getAmount());
            }

        return result;
    }

    @Autowired
    public ExpeditionController(ExpeditionRepository expeditionRepository, ExpeditionStageRepository stageRepository, SubscriptionExpeditionRepository subscriptionRepository, ParticipationExpeditionRepository participationRepository, HumanRepository humanRepository, HumanUtils humanUtils, CustomJMSSender jms) {
        this.expeditionRepository = expeditionRepository;
        this.stageRepository = stageRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.participationRepository = participationRepository;
        this.humanRepository = humanRepository;
        this.humanUtils = humanUtils;
        this.jms = jms;
    }
}
