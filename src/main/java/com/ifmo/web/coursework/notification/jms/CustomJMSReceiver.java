package com.ifmo.web.coursework.notification.jms;

import com.ifmo.web.coursework.notification.CustomMailSender;
import com.ifmo.web.coursework.notification.CustomXMPPSender;
import com.ifmo.web.coursework.notification.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJMSReceiver {
    private final CustomMailSender mailSender;
    private final CustomXMPPSender xmppSender;

    @JmsListener(destination = CustomJMSSender.JABBER)
    public void sendJabber(Message message) {
        xmppSender.send(message);
    }

    @JmsListener(destination = CustomJMSSender.MAIL)
    public void sendMail(Message message) {
        mailSender.sendMail(message);
    }

    @Autowired
    public CustomJMSReceiver(CustomMailSender mailSender, CustomXMPPSender xmppSender) {
        this.mailSender = mailSender;
        this.xmppSender = xmppSender;
    }
}
