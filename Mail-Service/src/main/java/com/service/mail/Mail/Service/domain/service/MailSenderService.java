package com.service.mail.Mail.Service.domain.service;

import com.service.mail.Mail.Service.domain.model.TicketCreatedDto;
import com.service.mail.Mail.Service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailSenderService {

    private final JavaMailSender javaMailSender;
    private final UsuarioRepository usuarioRepository;
    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Value("${spring.mail.username}")
    private String sender;

    public MailSenderService(JavaMailSender javaMailSender, UsuarioRepository usuarioRepository) {
        this.javaMailSender = javaMailSender;
        this.usuarioRepository = usuarioRepository;
    }

    public void sendEmail(String receiver, String subject, String text){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(receiver);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);

            javaMailSender.send(simpleMailMessage);
            logger.info("Email enviado para {}", receiver);
        } catch (Exception e){
            logger.error("Erro ao enviar e-mail para {}: {}", receiver, e.getMessage(), e);
        }
    }

    public void newTicketMail(TicketCreatedDto dto){
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
