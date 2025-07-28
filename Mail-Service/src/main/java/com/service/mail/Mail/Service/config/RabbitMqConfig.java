package com.service.mail.Mail.Service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.usuario.queue}")
    private String userQueue;
    @Value("${rabbitmq.ticket.queue}")
    private String ticketQueue;
    @Value("${rabbitmq.usuario.exchange}")
    private String userExchange;
    @Value("${rabbitmq.ticket.exchange}")
    private String ticketExchange;
    @Value("${rabbitmq.usuario.routingkey}")
    private String userRoutingKey;
    @Value("${rabbitmq.ticket.routingkey}")
    private String ticketRoutingKey;


    // Queues
    @Bean
    public Queue createUserQueue(){
        return QueueBuilder.durable(userQueue).build();
    }

    @Bean
    public Queue createTicketCreatedMailSendQueue(){
        return QueueBuilder.durable(ticketQueue).build();
    }

    // Exchanges
    @Bean
    public DirectExchange createUserExchange(){
        return ExchangeBuilder.directExchange(userExchange).build();
    }

    @Bean
    public DirectExchange createTicketExchange(){
        return ExchangeBuilder.directExchange(ticketExchange).build();
    }

    // Binds
    @Bean
    public Binding bindUser() {
        return BindingBuilder
                .bind(createUserQueue())
                .to(createUserExchange())
                .with(userRoutingKey);
    }

    @Bean
    public Binding bindTicketCreated() {
        return BindingBuilder
                .bind(createTicketCreatedMailSendQueue())
                .to(createTicketExchange())
                .with(ticketRoutingKey);
    }

    // ADMIN para criar filas exchanges...
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    //Message converter
    @Bean
    public MessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
