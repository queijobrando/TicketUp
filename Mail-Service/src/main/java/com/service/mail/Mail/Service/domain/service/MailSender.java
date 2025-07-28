package com.service.mail.Mail.Service.domain.service;

import com.service.mail.Mail.Service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class MailSender {

    protected final JavaMailSender javaMailSender;
    protected final UsuarioRepository usuarioRepository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.mail.username}")
    protected String sender;

    public MailSender(JavaMailSender javaMailSender, UsuarioRepository usuarioRepository) {
        this.javaMailSender = javaMailSender;
        this.usuarioRepository = usuarioRepository;
    }

    protected void sendEmail(String receiver, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
            logger.info("Email enviado para {}", receiver);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail para {}: {}", receiver, e.getMessage(), e);
        }
    }
}
