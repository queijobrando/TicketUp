package com.service.mail.Mail.Service.listener;

import com.service.mail.Mail.Service.domain.model.UsuarioInfoDto;
import com.service.mail.Mail.Service.domain.service.UsuarioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCadastroListener {

    private final UsuarioService usuarioService;

    public UsuarioCadastroListener(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RabbitListener(queues = "${rabbitmq.usuario.queue}")
    public void usuarioCadastro(UsuarioInfoDto dto){
        usuarioService.salvarDadosUsuario(dto);
    }

}
