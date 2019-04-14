package com.ifmo.web.coursework.config;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;

@Configuration
@PropertySource("classpath:jms.properties")
@EnableJms
public class JMSConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        return new RMQConnectionFactory();
    }
}
