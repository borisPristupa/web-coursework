package com.ifmo.web.coursework.data.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubscriptionAuctionPK implements Serializable {
    private Integer humanId;
    private Integer auctionId;
}
