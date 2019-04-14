package com.ifmo.web.coursework.notification;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@PropertySource("classpath:mail.properties")
public class CustomMailSender {
    @Getter
    @Value("${spring.mail.username}")
    private String defaultFrom;

    private final JavaMailSender sender;

    public void sendMail(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(Optional.ofNullable(message.getFrom()).orElse(defaultFrom));
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getText());

        sender.send(mailMessage);
    }

    @Autowired
    public CustomMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

}
