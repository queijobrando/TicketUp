package com.service.auth.Auth.Service.service;

import com.service.auth.Auth.Service.dto.UsuarioInfoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageRabbitMq {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.usuario.routingkey}")
    private String usuarioRoutingkey; // routingkey definida lá no application.yml

    public MessageRabbitMq(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void mandarMensagem(UsuarioInfoDto usuario, String exchange){
        rabbitTemplate.convertAndSend(exchange, usuarioRoutingkey, usuario);
    }

}
