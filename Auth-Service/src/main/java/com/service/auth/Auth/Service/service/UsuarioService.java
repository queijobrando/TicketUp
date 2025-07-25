package com.service.auth.Auth.Service.service;

import com.service.auth.Auth.Service.dto.LoginRequestDto;
import com.service.auth.Auth.Service.dto.LoginResponseDto;
import com.service.auth.Auth.Service.dto.RegisterRequestDto;
import com.service.auth.Auth.Service.model.Role;
import com.service.auth.Auth.Service.model.Usuario;
import com.service.auth.Auth.Service.repository.RoleRepository;
import com.service.auth.Auth.Service.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenJwtService tokenJwtService;

    public LoginResponseDto autenticarUsuario(LoginRequestDto dto){
        var usuario = usuarioRepository.findByLogin(dto.login());

        if (usuario.isEmpty() || !usuario.get().isLoginCorrect(dto, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Usuario ou senha inválido!");
        }

        var jwtValue = tokenJwtService.generateTokenClaimSet(usuario.get().getNome(), usuario.get().getRole().getNome());

        return new LoginResponseDto(jwtValue);
    }


    @Transactional
    public Usuario registrarUsuario(RegisterRequestDto dto){
        var usuarioNoBanco = usuarioRepository.findByLogin(dto.login());
        if (usuarioNoBanco.isPresent()){
            throw new RuntimeException("Já existe um usuário com esse login!");
        }

        Role role = roleRepository.findByNome(dto.role())
                .orElseThrow(() -> new EntityNotFoundException("Role inválida ou inexistente"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setEmail(dto.email());
        usuario.setSenha(bCryptPasswordEncoder.encode(dto.senha()));
        usuario.setRole(role);

        usuarioRepository.save(usuario);
        return usuario;
    }

}
