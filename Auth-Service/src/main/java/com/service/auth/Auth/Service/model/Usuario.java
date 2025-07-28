package com.service.auth.Auth.Service.model;

import com.service.auth.Auth.Service.dto.LoginRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity(name="usuario")
@Table(name="usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private boolean integrado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public boolean isLoginCorrect(LoginRequestDto loginRequest, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(loginRequest.senha(), this.senha);
    } // Autenticar senha

}
