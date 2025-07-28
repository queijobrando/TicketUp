package com.service.auth.Auth.Service.dto;

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
