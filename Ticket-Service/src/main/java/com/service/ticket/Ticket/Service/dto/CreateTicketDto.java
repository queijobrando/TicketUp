package com.service.ticket.Ticket.Service.dto;

public record CreateTicketDto(
        String titulo,
        String descricao,
        String loginSolicitante,
        String nomeSolicitante
) {
}
