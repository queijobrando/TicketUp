package com.service.ticket.Ticket.Service.service.agendador;

import com.service.ticket.Ticket.Service.domain.model.Ticket;
import com.service.ticket.Ticket.Service.dto.TicketCreatedDto;
import com.service.ticket.Ticket.Service.repository.TicketRepository;
import com.service.ticket.Ticket.Service.service.MessageRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TicketSemIntegracao {

    private final TicketRepository ticketRepository;
    private final MessageRabbitMq messageRabbitMq;
    private final Logger logger = LoggerFactory.getLogger(TicketSemIntegracao.class);

    @Value("${rabbitmq.ticket.exchange}")
    private String ticketExchange; // Exchange definida lá no application.yml

    public TicketSemIntegracao(TicketRepository ticketRepository, MessageRabbitMq messageRabbitMq) {
        this.ticketRepository = ticketRepository;
        this.messageRabbitMq = messageRabbitMq;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS) // a cada 10 segundos
    public void buscarTicketSemIntegracao(){
        ticketRepository.findAllByIntegradoIsFalse().forEach(ticket -> {
            try {
                TicketCreatedDto ticketResponse = new TicketCreatedDto(
                        ticket.id,
                        ticket.titulo,
                        ticket.descricao,
                        ticket.loginSolicitante,
                        ticket.nomeSolicitante,
                        ticket.atendente,
                        ticket.dataHoraAbertura,
                        ticket.dataHoraFechamento
                );
                messageRabbitMq.mandarMensagemNovoTicket(ticketResponse, ticketExchange);
                atualizarTicket(ticket);
            } catch (RuntimeException e){
                logger.error(e.getMessage());
            }
        });
    }

    private void atualizarTicket(Ticket ticket){
        ticket.setIntegrado(true);
        ticketRepository.save(ticket);
    }

}

