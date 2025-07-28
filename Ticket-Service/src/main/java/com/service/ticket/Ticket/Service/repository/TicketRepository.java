package com.service.ticket.Ticket.Service.repository;

import com.service.ticket.Ticket.Service.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findAllByIntegradoIsFalse();

}
