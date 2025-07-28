package com.service.mail.Mail.Service.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name="usuarios")
@Table(name="usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String login;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String sobrenome;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String role;

}
