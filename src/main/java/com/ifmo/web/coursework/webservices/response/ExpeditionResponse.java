package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Expedition;
import com.ifmo.web.coursework.data.entity.ExpeditionStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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
                .build();
    }
}
