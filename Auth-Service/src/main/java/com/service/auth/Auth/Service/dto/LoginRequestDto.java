package com.service.auth.Auth.Service.dto;

public record LoginRequestDto(
        String login,
        String senha
) {
}
