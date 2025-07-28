package com.service.mail.Mail.Service.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketCreatedDto(
        UUID id,
        String titulo,
        String descricao,
        String loginSolicitante,
        String nomeSolicitante,
        String atendente,
        LocalDateTime dataHoraAbertura,
        LocalDateTime dataHoraFechamento
) {
}
