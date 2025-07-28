package com.service.auth.Auth.Service.service;

import com.service.auth.Auth.Service.dto.LoginRequestDto;
import com.service.auth.Auth.Service.dto.LoginResponseDto;
import com.service.auth.Auth.Service.dto.RegisterRequestDto;
import com.service.auth.Auth.Service.dto.UsuarioInfoDto;
import com.service.auth.Auth.Service.model.Role;
import com.service.auth.Auth.Service.model.Usuario;
import com.service.auth.Auth.Service.repository.RoleRepository;
import com.service.auth.Auth.Service.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenJwtService tokenJwtService;
    private final MessageRabbitMq messageRabbitMq;

    @Value("${rabbitmq.usuario.exchange}")
    private String usuarioExchange; // Exchange definida lá no application.yml

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenJwtService tokenJwtService, MessageRabbitMq messageRabbitMq) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenJwtService = tokenJwtService;
        this.messageRabbitMq = messageRabbitMq;
    }

    public LoginResponseDto autenticarUsuario(LoginRequestDto dto){
        var usuario = usuarioRepository.findByLogin(dto.login());

        if (usuario.isEmpty() || !usuario.get().isLoginCorrect(dto, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Usuario ou senha inválido!");
        }

        var jwtValue = tokenJwtService.generateTokenClaimSet(usuario.get().getLogin(), usuario.get().getNome(), usuario.get().getSobrenome(), usuario.get().getRole().getNome());

        return new LoginResponseDto(jwtValue);
    }

    @Transactional
    public void mensagem(Usuario usuario){
        try {
            UsuarioInfoDto usuarioRetorno = new UsuarioInfoDto(
                    usuario.getId(),
                    usuario.getLogin(),
                    usuario.getNome(),
                    usuario.getSobrenome(),
                    usuario.getEmail(),
                    usuario.getRole().getNome());
            messageRabbitMq.mandarMensagem(usuarioRetorno, usuarioExchange);
        } catch (RuntimeException e){
            usuario.setIntegrado(false);
            usuarioRepository.save(usuario);
        }
    }


    @Transactional
    public UsuarioInfoDto registrarUsuario(RegisterRequestDto dto){
        var usuarioNoBanco = usuarioRepository.findByLogin(dto.login());
        if (usuarioNoBanco.isPresent()){
            throw new RuntimeException("Já existe um usuário com esse login!");
        }

        Role role = roleRepository.findByNome(dto.role())
                .orElseThrow(() -> new EntityNotFoundException("Role inválida ou inexistente"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setLogin(dto.login());
        usuario.setEmail(dto.email());
        usuario.setSenha(bCryptPasswordEncoder.encode(dto.senha()));
        usuario.setRole(role);
        usuario.setIntegrado(true);

        usuarioRepository.save(usuario);
        mensagem(usuario); // Mandar mensagem
        return new UsuarioInfoDto(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getRole().getNome()
        );
    }

}
