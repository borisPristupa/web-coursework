package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Artifact;
import com.ifmo.web.coursework.data.entity.Category;
import com.ifmo.web.coursework.data.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtifactResponse {
    private Integer id;
    private String name, description, type;
    private AuctionResponse auction;
    private Boolean approved, banned;
    private HumanResponse owner, approver;
    private String country, age;

    public static ArtifactResponse fromArtifact(Artifact artifact) {
        return builder()
                .id(artifact.getArtifactId())
                .name(artifact.getName())
                .type(Optional.ofNullable(artifact.getCategoryByCategoryId())
                        .map(Category::getName)
                        .orElse(null))
                .description(artifact.getDescription())
                .country(Optional.ofNullable(artifact.getCountryByCountryId())
                        .map(Country::getName)
                        .orElse(null))
                .age(artifact.getAgeByAgeId().getDescription())
                .auction(AuctionResponse.fromAuction(artifact.getAuctionByArtifactId()))

                .approved(artifact.getApproved())
                .banned(artifact.getBanned())

                .owner(HumanResponse.fromHuman(artifact.getHumanByOwner()))
                .approver(HumanResponse.fromHuman(artifact.getHumanByApprover()))
                .build();
    }
}
