package com.service.ticket.Ticket.Service.service;

import com.service.ticket.Ticket.Service.domain.model.Ticket;
import com.service.ticket.Ticket.Service.domain.model.enun.TicketStatus;
import com.service.ticket.Ticket.Service.dto.CreateTicketDto;
import com.service.ticket.Ticket.Service.dto.TicketCreatedDto;
import com.service.ticket.Ticket.Service.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

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


    public TicketCreatedDto criarTicket(CreateTicketDto dto){
        Ticket ticket = gerarTicket(dto);
        ticketRepository.save(ticket);

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
