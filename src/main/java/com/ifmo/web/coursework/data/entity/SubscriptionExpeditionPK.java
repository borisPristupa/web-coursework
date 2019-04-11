package com.ifmo.web.coursework.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
public class SubscriptionExpeditionPK implements Serializable {
    private Integer humanId;
    private Integer expeditionId;
}
