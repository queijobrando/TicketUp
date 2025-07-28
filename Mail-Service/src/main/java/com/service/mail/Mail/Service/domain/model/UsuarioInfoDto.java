package com.service.mail.Mail.Service.domain.model;

import java.util.UUID;

public record UsuarioInfoDto(
        UUID id,
        String login,
        String nome,
        String sobrenome,
        String email,
        String role
) {
}
