package com.service.mail.Mail.Service.repository;

import com.service.mail.Mail.Service.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("SELECT u.email FROM usuarios u WHERE u.role = 'ADMIN'")
    List<String> allAdminsEmails();

}
