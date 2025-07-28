package com.service.mail.Mail.Service.domain.service;

import com.service.mail.Mail.Service.domain.model.Usuario;
import com.service.mail.Mail.Service.domain.model.UsuarioInfoDto;
import com.service.mail.Mail.Service.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void salvarDadosUsuario(UsuarioInfoDto dto){
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setRole(dto.role());

        usuarioRepository.save(usuario);
    }

}
