package com.service.auth.Auth.Service.dto;

public record RegisterRequestDto(
        String login,
        String nome,
        String sobrenome,
        String email,
        String senha,
        String role
) {
}
