package com.service.auth.Auth.Service.config;

import com.service.auth.Auth.Service.model.Role;
import com.service.auth.Auth.Service.model.Usuario;
import com.service.auth.Auth.Service.model.enun.RoleValues;
import com.service.auth.Auth.Service.repository.RoleRepository;
import com.service.auth.Auth.Service.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${default.admin.password}")
    private String senha;

    public AdminUserConfig(UsuarioRepository usuarioRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByLogin("admin").isEmpty()) {
            Role adminRole = roleRepository.findByNome(RoleValues.ADMIN.name())
                    .orElseThrow(() -> new RuntimeException("Role ADMIN não encontrada!"));

            Usuario usuario = new Usuario();
            usuario.setLogin("admin");
            usuario.setNome("Admin");
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setRole(adminRole);

            usuarioRepository.save(usuario);
            System.out.println("Usuário admin criado com sucesso.");
        } else {
            System.out.println("Usuário admin já existe.");
        }
    }
}
