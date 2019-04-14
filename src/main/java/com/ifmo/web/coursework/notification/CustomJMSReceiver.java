package com.ifmo.web.coursework.notification;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJMSReceiver {
    @JmsListener(destination = "cus")
    public void receive(Message message) {
        System.out.println("Received: " + message.getText());
    }

    @JmsListener(destination = "cus")
    public void receive2(Message message) {
        System.out.println("Received2222: " + message.getText());
    }
}
