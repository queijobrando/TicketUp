package com.service.auth.Auth.Service.controller;

import com.service.auth.Auth.Service.dto.RegisterRequestDto;
import com.service.auth.Auth.Service.dto.UsuarioInfoDto;
import com.service.auth.Auth.Service.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController implements GenericController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioInfoDto> registrar(@RequestBody RegisterRequestDto dto){
        UsuarioInfoDto usuario = usuarioService.registrarUsuario(dto);

        URI location = generateHeaderLocation(usuario.id());

        return ResponseEntity.created(location).body(usuario);
    }

}
