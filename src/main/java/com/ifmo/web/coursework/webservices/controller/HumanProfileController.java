package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import com.ifmo.web.coursework.data.utils.FilterUtils;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.exception.AlreadyExistsException;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.exception.PermissionDeniedException;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/human")
public class HumanProfileController {
    private final HumanRepository humanRepository;
    private final HumanUtils humanUtils;
    private final FilterUtils filterUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse getProfile(@RequestParam(value = "id", required = false) Integer id) {
        if (null == id) id = humanUtils.getCurrentId();

        Integer finalId = id;
        return HumanResponse.fromHuman(humanRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("No user with id '" + finalId + "' found in DB")));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public HumanResponse updateProfile(HumanResponse newProfile) {
        // Unique data
        if (null == newProfile.getId()) throw new MissingRequiredArgumentException("id");
        if (newProfile.getId() != humanUtils.getCurrentId())
            throw new PermissionDeniedException("You are not allowed to " +
                    "modify other user's personal info");

        Human edited = humanRepository.findById(newProfile.getId())
                .orElseThrow(() -> new NotFoundException("User not found by id " + newProfile.getId()));

        if (null != newProfile.getUsername()) {
            humanRepository.findByLogin(newProfile.getUsername()).ifPresent(human -> {
                if (!edited.getHumanId().equals(human.getHumanId()))
                    throw new AlreadyExistsException("Username " + newProfile.getUsername() + " is already taken");
            });
            edited.setLogin(newProfile.getUsername());
        }

        if (null != newProfile.getEmail()) {
            humanRepository.findByEmail(newProfile.getEmail()).ifPresent(human -> {
                if (!edited.getHumanId().equals(human.getHumanId()))
                    throw new AlreadyExistsException("Email " + newProfile.getEmail() + " is already registered");
            });
            edited.setEmail(newProfile.getEmail());
        }

        if (null != newProfile.getTg_nickname()) {
            humanRepository.findByTgNickname(newProfile.getTg_nickname()).ifPresent(human -> {
                if (!edited.getHumanId().equals(human.getHumanId()))
                    throw new AlreadyExistsException("Telegram nickname " + newProfile.getTg_nickname() + " is already registered");
            });
            edited.setEmail(newProfile.getTg_nickname());
        }

        // Non-unique data:

        if (null != newProfile.getFirst_name())
            edited.setFirstName(newProfile.getFirst_name());

        if (null != newProfile.getSecond_name())
            edited.setSecondName(newProfile.getSecond_name());

        if (null != newProfile.getLast_name())
            edited.setLastName(newProfile.getLast_name());

        // Not important data:

        if (null != newProfile.getBio())
            edited.setBio(newProfile.getBio());

        if (edited.getLikes() < newProfile.getLikes())
            edited.setLikes(newProfile.getLikes());

        if (edited.getDislikes() < newProfile.getDislikes())
            edited.setDislikes(newProfile.getDislikes());

        if (null != newProfile.getBanned())
            edited.setBanned(newProfile.getBanned());

        if (null != newProfile.getCountry())
            edited.setCountryId(newProfile.getCountry().getId());

        // Roles:

        if (null != newProfile.getArchaeologist() &&
                !newProfile.getArchaeologist().equals(edited.getArchaeologist()))
            edited.setArchaeologist(newProfile.getArchaeologist());

        if (null != newProfile.getCollector() &&
                !newProfile.getCollector().equals(edited.getCollector()))
            edited.setCollector(newProfile.getCollector());

        if (null != newProfile.getSponsor() &&
                !newProfile.getSponsor().equals(edited.getSponsor()))
            edited.setSponsor(newProfile.getSponsor());

        // Privileged roles:

        if (Boolean.FALSE.equals(newProfile.getResearcher()))
            edited.setResearcher(newProfile.getResearcher());

        if (Boolean.FALSE.equals(newProfile.getModerator()))
            edited.setModerator(newProfile.getModerator());

        humanRepository.save(edited);
        return HumanResponse.fromHuman(edited);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/privileged")
    public HumanResponse makePrivileged(@RequestParam("id") int id,
                                        @RequestParam(value = "researcher", required = false) Boolean researcher,
                                        @RequestParam(value = "moderator", required = false) Boolean moderator) {
        Human edited = humanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id " + id));

        if (null != researcher && !researcher.equals(edited.getResearcher()))
            edited.setResearcher(researcher);

        if (null != moderator && !moderator.equals(edited.getModerator()))
            edited.setModerator(moderator);

        humanRepository.save(edited);
        return HumanResponse.fromHuman(edited);
    }

    @GetMapping("/search")
    public List<HumanResponse> search(@RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                      @RequestParam("archaeologist") boolean archaeologist,
                                      @RequestParam("researcher") boolean researcher,
                                      @RequestParam("collector") boolean collector,
                                      @RequestParam("sponsor") boolean sponsor,
                                      @RequestParam(value = "searchfor", required = false, defaultValue = "") String pattern) {
        return humanRepository.findAll().stream()
                .filter(filterUtils.nameFilter(pattern, Human::getFirstName, Human::getSecondName, Human::getLastName))
                .filter(h ->
                        archaeologist && h.getArchaeologist() ||
                                researcher && h.getResearcher() ||
                                collector && h.getCollector() ||
                                sponsor && h.getSponsor() ||
                                !h.getArchaeologist() && !h.getResearcher() && !h.getCollector() && !h.getSponsor())
                .filter(h -> humanUtils.getCurrentId() != h.getHumanId())
                .limit(amount)
                .sorted(Comparator.comparingInt(Human::getHumanId))
                .map(HumanResponse::fromHuman)
                .collect(Collectors.toList());
    }

    @Autowired
    public HumanProfileController(HumanRepository humanRepository, HumanUtils humanUtils, FilterUtils filterUtils) {
        this.humanRepository = humanRepository;
        this.humanUtils = humanUtils;
        this.filterUtils = filterUtils;
    }
}
