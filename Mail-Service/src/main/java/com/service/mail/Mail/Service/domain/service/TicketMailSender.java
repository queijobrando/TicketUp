package com.service.mail.Mail.Service.domain.service;

import com.service.mail.Mail.Service.domain.model.TicketCreatedDto;
import com.service.mail.Mail.Service.repository.UsuarioRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TicketMailSender extends MailSender {

    public TicketMailSender(JavaMailSender javaMailSender, UsuarioRepository usuarioRepository) {
        super(javaMailSender, usuarioRepository);
    }

    public void sendNewTicketMail(TicketCreatedDto dto) {
        var emails = usuarioRepository.allAdminsEmails();
        if (emails.isEmpty()) {
            logger.warn("Nenhum e-mail de administrador encontrado para envio de novo ticket.");
            return;
        }

        String message = String.format(
                "Novo ticket criado!\n\nTítulo: %s\nDescrição: %s\nSolicitante: %s (%s)\n\nAcesse o sistema para mais detalhes.",
                dto.titulo(),
                dto.descricao(),
                dto.nomeSolicitante(),
                dto.loginSolicitante()
        );

        emails.forEach(email -> sendEmail(email, "Novo ticket criado", message));
    }
}
