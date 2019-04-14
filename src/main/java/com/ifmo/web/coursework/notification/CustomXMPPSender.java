package com.ifmo.web.coursework.notification;

import lombok.Getter;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@PropertySource("classpath:jabber.properties")
public class CustomXMPPSender {
    @Getter
    @Value("${jid}")
    private String defaultFrom; // your.simple.jabber@jabb3r.org

    @Value("${password}")
    private String password;

    @Value("${host}") // jabb3r.org
    private String host;

    public void send(Message message) {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(message.getFrom().split("@")[0], password)
                    .setXmppDomain(message.getFrom().split("@")[1])
                    .setHost(host)
                    .build();


            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();
            connection.login();

            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            EntityBareJid jid = JidCreate.entityBareFrom(message.getTo());
            Chat chat = chatManager.chatWith(jid);
            chat.send(message.getText());

            connection.disconnect();
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
