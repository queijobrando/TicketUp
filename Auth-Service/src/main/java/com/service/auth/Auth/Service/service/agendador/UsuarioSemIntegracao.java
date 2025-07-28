package com.service.auth.Auth.Service.service.agendador;

import com.service.auth.Auth.Service.dto.UsuarioInfoDto;
import com.service.auth.Auth.Service.model.Usuario;
import com.service.auth.Auth.Service.repository.UsuarioRepository;
import com.service.auth.Auth.Service.service.MessageRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UsuarioSemIntegracao {

    private final UsuarioRepository usuarioRepository;
    private final MessageRabbitMq messageRabbitMq;
    private final Logger logger = LoggerFactory.getLogger(UsuarioSemIntegracao.class);

    @Value("${rabbitmq.usuario.exchange}")
    private String usuarioExchange; // Exchange definida lá no application.yml

    public UsuarioSemIntegracao(UsuarioRepository usuarioRepository, MessageRabbitMq messageRabbitMq) {
        this.usuarioRepository = usuarioRepository;
        this.messageRabbitMq = messageRabbitMq;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS) // a cada 10 segundos
    public void buscarUsuarioSemIntegracao(){
        usuarioRepository.findAllByIntegradoIsFalse().forEach(usuario -> {
            try {
                UsuarioInfoDto usuarioRetorno = new UsuarioInfoDto(
                        usuario.getId(),
                        usuario.getLogin(),
                        usuario.getNome(),
                        usuario.getSobrenome(),
                        usuario.getEmail(),
                        usuario.getRole().getNome());
                messageRabbitMq.mandarMensagem(usuarioRetorno, usuarioExchange);
                atualizarProposta(usuario);
            } catch (RuntimeException e){
                logger.error(e.getMessage());
            }
        });
    }

    private void atualizarProposta(Usuario usuario){
        usuario.setIntegrado(true);
        usuarioRepository.save(usuario);
    }

}
