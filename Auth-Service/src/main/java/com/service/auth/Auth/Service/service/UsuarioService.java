package com.service.auth.Auth.Service.service;

import com.service.auth.Auth.Service.dto.LoginRequestDto;
import com.service.auth.Auth.Service.dto.LoginResponseDto;
import com.service.auth.Auth.Service.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenJwtService tokenJwtService;

    public LoginResponseDto autenticarUsuario(LoginRequestDto dto){
        var usuario = usuarioRepository.findByLogin(dto.login());

        if (usuario.isEmpty() || usuario.get().isLoginCorrect(dto, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Usuario ou senha inválido!");
        }

        var jwtValue = tokenJwtService.generateTokenClaimSet(usuario.get().getId());

        return new LoginResponseDto(jwtValue);
    }

}
