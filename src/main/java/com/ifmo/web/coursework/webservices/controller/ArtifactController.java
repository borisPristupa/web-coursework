package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.data.entity.Artifact;
import com.ifmo.web.coursework.data.entity.Category;
import com.ifmo.web.coursework.data.repository.AgeRepository;
import com.ifmo.web.coursework.data.repository.ArtifactRepository;
import com.ifmo.web.coursework.data.repository.CategoryRepository;
import com.ifmo.web.coursework.data.repository.CountryRepository;
import com.ifmo.web.coursework.data.utils.HumanUtils;
import com.ifmo.web.coursework.webservices.exception.MissingRequiredArgumentException;
import com.ifmo.web.coursework.webservices.exception.NotFoundException;
import com.ifmo.web.coursework.webservices.response.ArtifactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {
    private final ArtifactRepository artifactRepository;
    private final CategoryRepository categoryRepository;
    private final AgeRepository ageRepository;
    private final CountryRepository countryRepository;
    private final HumanUtils humanUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ArtifactResponse getArtifact(@RequestParam("id") int id) {
        return ArtifactResponse.fromArtifact(artifactRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found artifact by id " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtifactResponse addArtifact(ArtifactResponse artifactResponse) {
        ArrayList<String> missing = new ArrayList<>();
        if (null == artifactResponse.getType())
            missing.add("type");
        if (null == artifactResponse.getName())
            missing.add("name");
        if (null == artifactResponse.getAge())
            missing.add("age");
        if (null == artifactResponse.getCountry())
            missing.add("country");

        if (!missing.isEmpty())
            throw new MissingRequiredArgumentException(missing.toArray(new String[0]));

        Artifact created = new Artifact();

        created.setCategoryByCategoryId(
                categoryRepository.findByName(artifactResponse.getType())
                        .orElseThrow(() ->
                                new NotFoundException("Not found artifact type '" + artifactResponse.getType() + "'")));
        created.setAgeByAgeId(
                ageRepository.findByDescription(artifactResponse.getAge())
                        .orElseThrow(() ->
                                new NotFoundException("Not found age '" + artifactResponse.getAge() + "'")));
        created.setCountryByCountryId(
                countryRepository.findByName(artifactResponse.getCountry())
                        .orElseThrow(() ->
                                new NotFoundException("Not found country '" + artifactResponse.getCountry() + "'")));

        created.setName(artifactResponse.getName());
        if (null != artifactResponse.getDescription())
            created.setDescription(artifactResponse.getDescription());
        created.setBanned(false);
        created.setHumanByOwner(humanUtils.getCurrentHuman());
        created.setApproved(false);

        if (Boolean.TRUE.equals(humanUtils.getCurrentHuman().getResearcher())) {
            created.setApproved(true);
            created.setHumanByApprover(humanUtils.getCurrentHuman());
        }

        artifactRepository.save(created);
        artifactRepository.flush();
        return ArtifactResponse.fromArtifact(
                artifactRepository.findOne(Example.of(created))
                        .orElseGet(() ->
                                artifactRepository.findFirstByOrderByArtifactIdDesc()
                                        .orElseThrow(() ->
                                                new IllegalStateException("Could not save a new artifact '" +
                                                        created.getName() + "'"))
                        ));
    }

    @PatchMapping("/privileged/ban")
    @ResponseStatus(HttpStatus.OK)
    public ArtifactResponse ban(@RequestParam("id") Integer id,
                                @RequestParam("banned") Boolean banned) {
        Artifact artifact = artifactRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Artifact not found by id '" + id + "'"));
        artifact.setBanned(banned);
        artifactRepository.save(artifact);
        return ArtifactResponse.fromArtifact(artifact);
    }

    @PatchMapping("/privileged/approve")
    @ResponseStatus(HttpStatus.OK)
    public ArtifactResponse approve(@RequestParam("id") Integer id,
                                    @RequestParam("approved") Boolean approved) {
        Artifact artifact = artifactRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Artifact not found by id '" + id + "'"));
        artifact.setApproved(approved);
        artifact.setHumanByApprover(humanUtils.getCurrentHuman());
        artifactRepository.save(artifact);
        return ArtifactResponse.fromArtifact(artifact);

    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtifactResponse> search(@RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "on_auction", required = false) Boolean auctioned,
                                         @RequestParam("types[]") List<String> types,
                                         @RequestParam("ages[]") List<String> ages,
                                         @RequestParam("country") String country,
                                         @RequestParam(value = "price_min", required = false) Integer priceMin,
                                         @RequestParam(value = "price_max", required = false) Integer priceMax) {
        if (!countryRepository.findByName(country).isPresent())
            throw new NotFoundException("Country not found '" + country + "'");

        List<Artifact> artifacts;
        Pageable pages = PageRequest.of(page, amount);

        if (null == auctioned)
            artifacts = artifactRepository.findAllByBannedIsFalse(pages);
        else if (auctioned)
            artifacts = artifactRepository.findAllAuctionByArtifactIdNotNullAndBannedIsFalse(pages).stream()
                    .filter(artifact -> priceMin == null || artifact.getAuctionByArtifactId().getPriceNew() >= priceMin)
                    .filter(artifact -> priceMax == null || artifact.getAuctionByArtifactId().getPriceNew() <= priceMax)
                    .collect(Collectors.toList());
        else
            artifacts = artifactRepository.findAllAuctionByArtifactIdIsNullAndBannedIsFalse(pages);

        return artifacts.stream()
                .map(ArtifactResponse::fromArtifact)
                .filter(artifactResponse -> types.isEmpty() || types.contains(artifactResponse.getType()))
                .filter(artifactResponse -> ages.isEmpty() || ages.contains(artifactResponse.getAge()))
                .filter(artifactResponse -> country.equals(artifactResponse.getCountry()))
                .collect(Collectors.toList());
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getTypes() {
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    @Autowired
    public ArtifactController(ArtifactRepository artifactRepository, CategoryRepository categoryRepository, AgeRepository ageRepository, CountryRepository countryRepository, HumanUtils humanUtils) {
        this.artifactRepository = artifactRepository;
        this.categoryRepository = categoryRepository;
        this.ageRepository = ageRepository;
        this.countryRepository = countryRepository;
        this.humanUtils = humanUtils;
    }
}