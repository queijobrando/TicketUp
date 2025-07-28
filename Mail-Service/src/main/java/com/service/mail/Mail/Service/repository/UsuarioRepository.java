package com.service.mail.Mail.Service.repository;

import com.service.mail.Mail.Service.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
