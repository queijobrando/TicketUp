package com.service.ticket.Ticket.Service.controller;

import com.service.ticket.Ticket.Service.dto.CreateTicketDto;
import com.service.ticket.Ticket.Service.dto.TicketCreatedDto;
import com.service.ticket.Ticket.Service.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("ticket")
public class TicketController implements GenericController {

    public final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketCreatedDto> criarTicket(@RequestBody CreateTicketDto dto){
        TicketCreatedDto ticket = ticketService.criarTicket(dto);

        URI location = generateHeaderLocation(ticket.id());

        return ResponseEntity.created(location).body(ticket);
    }

}
