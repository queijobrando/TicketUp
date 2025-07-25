package com.service.auth.Auth.Service.controller;

import com.service.auth.Auth.Service.dto.LoginRequestDto;
import com.service.auth.Auth.Service.dto.LoginResponseDto;
import com.service.auth.Auth.Service.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        LoginResponseDto response = usuarioService.autenticarUsuario(dto);

        return ResponseEntity.ok(response);
    }
}
