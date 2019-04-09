package com.ifmo.web.coursework.webservices.response;

import com.ifmo.web.coursework.data.entity.Auction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionResponse {
    private Integer id, artifact_id;
    private Integer price_old, price_new;
    private HumanResponse raiser;
    private Timestamp bet_time, start_time, end_time;

    public static AuctionResponse fromAuction(Auction auction) {
        if (null == auction) return null;
        return builder()
                .id(auction.getAuctionId())
                .artifact_id(auction.getArtifactId())
                .price_new(auction.getPriceNew())
                .price_old(auction.getPriceOld())
                .raiser(HumanResponse.fromHuman(auction.getHumanByRaiser()))
                .bet_time(auction.getBetTime())
                .start_time(auction.getStartTime())
                .end_time(auction.getEndTime())
                .build();
    }
}
