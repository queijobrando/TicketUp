package com.service.ticket.Ticket.Service.service;

import com.service.ticket.Ticket.Service.domain.model.Ticket;
import com.service.ticket.Ticket.Service.domain.model.enun.TicketStatus;
import com.service.ticket.Ticket.Service.dto.CreateTicketDto;
import com.service.ticket.Ticket.Service.dto.TicketCreatedDto;
import com.service.ticket.Ticket.Service.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MessageRabbitMq messageRabbitMq;

    public TicketService(TicketRepository ticketRepository, MessageRabbitMq messageRabbitMq) {
        this.ticketRepository = ticketRepository;
        this.messageRabbitMq = messageRabbitMq;
    }

    @Value("${rabbitmq.ticket.exchange}")
    private String ticketExchange;

    public Ticket gerarTicket(CreateTicketDto dto){
        Ticket ticket = new Ticket();
        ticket.setTitulo(dto.titulo());
        ticket.setDescricao(dto.descricao());
        ticket.setStatus(TicketStatus.ABERTO);
        ticket.setLoginSolicitante(dto.loginSolicitante());
        ticket.setNomeSolicitante(dto.nomeSolicitante());
        ticket.setDataHoraAbertura(LocalDateTime.now());
        ticket.setIntegrado(true);
        return ticket;
    }

    @Transactional
    public void mensagemNovoTicket(Ticket ticket){
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
        } catch (RuntimeException e){
            ticket.setIntegrado(false);
            ticketRepository.save(ticket);
        }
    }


    @Transactional
    public TicketCreatedDto criarTicket(CreateTicketDto dto){
        Ticket ticket = gerarTicket(dto);
        ticketRepository.save(ticket);

        mensagemNovoTicket(ticket); // Mandar a mensagem

        return new TicketCreatedDto(
                ticket.id,
                ticket.titulo,
                ticket.descricao,
                ticket.loginSolicitante,
                ticket.nomeSolicitante,
                ticket.atendente,
                ticket.dataHoraAbertura,
                ticket.dataHoraFechamento
        );
    }




}
