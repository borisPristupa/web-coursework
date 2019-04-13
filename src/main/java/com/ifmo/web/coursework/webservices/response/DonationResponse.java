package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Donation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationResponse {
    private Integer id;
    private Integer expedition_id;
    private HumanResponse donator;
    private Integer amount;

    public static DonationResponse fromDonation(Donation donation) {
        if (null == donation) return null;

        return builder()
                .id(donation.getDonationId())
                .expedition_id(donation.getExpeditionId())
                .donator(HumanResponse.fromHuman(donation.getHumanByDonator()))
                .amount(donation.getAmount())
                .build();
    }
}
