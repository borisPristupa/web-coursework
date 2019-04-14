package com.ifmo.web.coursework.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class CustomJMSSender {
    private final JmsTemplate jmsTemplate;

    public void send(String destination, Message message) {
        jmsTemplate.send(destination, session ->
                session.createObjectMessage(message));
    }

    @Autowired
    public CustomJMSSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
