package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Expedition;
import com.ifmo.web.coursework.data.entity.ExpeditionStage;
import com.ifmo.web.coursework.data.entity.ParticipationExpedition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpeditionResponse {
    private Integer id;
    private String name, description;
    private Boolean banned;
    private Integer full_sum, current_sum;
    private HumanResponse head;
    private String stage;
    private List<HumanResponse> members;

    public static ExpeditionResponse fromExpedition(Expedition expedition) {
        if (null == expedition) return null;

        return builder()
                .id(expedition.getExpeditionId())
                .name(expedition.getName())
                .description(expedition.getDescription())
                .head(HumanResponse.fromHuman(expedition.getHumanByHead()))
                .banned(expedition.getBanned())
                .full_sum(expedition.getCosts())
                .current_sum(expedition.getCurrentSum())
                .stage(Optional.ofNullable(expedition.getExpeditionStageByStageId())
                        .map(ExpeditionStage::getName)
                        .orElse(null))
                .members(expedition.getParticipationExpeditionsByExpeditionId().stream()
                        .map(ParticipationExpedition::getHumanByHumanId)
                        .map(HumanResponse::fromHuman)
                        .collect(Collectors.toList()))
                .build();
    }
}
