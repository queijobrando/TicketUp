package com.service.mail.Mail.Service.listener;

import com.service.mail.Mail.Service.domain.model.TicketCreatedDto;
import com.service.mail.Mail.Service.domain.model.UsuarioInfoDto;
import com.service.mail.Mail.Service.domain.service.MailSenderService;
import com.service.mail.Mail.Service.domain.service.UsuarioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailListener {

    private final UsuarioService usuarioService;
    private final MailSenderService mailSenderService;

    public MailListener(UsuarioService usuarioService, MailSenderService mailSenderService) {
        this.usuarioService = usuarioService;
        this.mailSenderService = mailSenderService;
    }


    @RabbitListener(queues = "${rabbitmq.usuario.queue}")
    public void usuarioCadastro(UsuarioInfoDto dto){
        usuarioService.salvarDadosUsuario(dto);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.queue}")
    public void ticketCriado(TicketCreatedDto dto){
        mailSenderService.newTicketMail(dto);
    }

}
