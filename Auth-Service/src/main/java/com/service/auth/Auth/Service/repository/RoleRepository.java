package com.service.auth.Auth.Service.repository;

import com.service.auth.Auth.Service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNome(String nome);

}
