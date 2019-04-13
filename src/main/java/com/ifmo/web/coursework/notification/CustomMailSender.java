package com.ifmo.web.coursework.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mail.properties")
public class CustomMailSender {
    @Value("${spring.mail.username}")
    String from;

    private final JavaMailSender sender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        sender.send(message);
    }

    @Autowired
    public CustomMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

}
