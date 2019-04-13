package com.ifmo.web.coursework.notification;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Message implements Serializable {
    private String from, to, subject, text;
}
