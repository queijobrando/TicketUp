package com.service.ticket.Ticket.Service.domain.model;

import com.service.ticket.Ticket.Service.domain.model.enun.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="tickets")
@Table(name="tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String titulo;

    @Column(nullable = false)
    public String descricao;

    @Column(nullable = false)
    @Enumerated
    public TicketStatus status;

    @Column(nullable = false)
    public String loginSolicitante;

    public String nomeSolicitante;

    public String atendente;

    @Column(nullable = false)
    public LocalDateTime dataHoraAbertura;

    public LocalDateTime dataHoraFechamento;

    public boolean integrado;

}
