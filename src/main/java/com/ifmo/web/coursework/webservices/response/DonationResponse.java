package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Donation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationResponse {
    private Integer id;
    private ExpeditionResponse expedition;
    private HumanResponse donator;
    private Integer amount;
    private Timestamp time;

    public static DonationResponse fromDonation(Donation donation) {
        if (null == donation) return null;

        return builder()
                .id(donation.getDonationId())
                .expedition(ExpeditionResponse.fromExpedition(donation.getExpeditionByExpeditionId()))
                .donator(HumanResponse.fromHuman(donation.getHumanByDonator()))
                .amount(donation.getAmount())
                .time(donation.getTime())
                .build();
    }
}
