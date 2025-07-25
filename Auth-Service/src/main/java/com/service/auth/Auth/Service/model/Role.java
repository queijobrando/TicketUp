package com.service.auth.Auth.Service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="roles")
@Table(name="roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}
