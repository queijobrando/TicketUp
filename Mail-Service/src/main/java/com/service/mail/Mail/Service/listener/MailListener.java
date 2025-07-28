package com.service.mail.Mail.Service.listener;

import com.service.mail.Mail.Service.domain.model.TicketCreatedDto;
import com.service.mail.Mail.Service.domain.model.UsuarioInfoDto;
import com.service.mail.Mail.Service.domain.service.TicketMailSender;
import com.service.mail.Mail.Service.domain.service.UsuarioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailListener {

    private final UsuarioService usuarioService;
    private final TicketMailSender ticketMailSender;

    public MailListener(UsuarioService usuarioService, TicketMailSender ticketMailSender) {
        this.usuarioService = usuarioService;
        this.ticketMailSender = ticketMailSender;
    }


    @RabbitListener(queues = "${rabbitmq.usuario.queue}")
    public void usuarioCadastro(UsuarioInfoDto dto){
        usuarioService.salvarDadosUsuario(dto);
    }

    @RabbitListener(queues = "${rabbitmq.ticket.queue}")
    public void ticketCriado(TicketCreatedDto dto){
        ticketMailSender.sendNewTicketMail(dto);
    }

}
