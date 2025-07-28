package com.service.ticket.Ticket.Service.service;

import com.service.ticket.Ticket.Service.dto.TicketCreatedDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageRabbitMq {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.ticket.routingkey.created-ticket}")
    private String ticketCriadoRoutingkey; // routingkey definida lá no application.yml

    public MessageRabbitMq(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void mandarMensagemNovoTicket(TicketCreatedDto ticket, String exchange){
        rabbitTemplate.convertAndSend(exchange, ticketCriadoRoutingkey, ticket);
    }

}
