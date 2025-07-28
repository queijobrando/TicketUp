package com.service.auth.Auth.Service.service;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenJwtService {

    private final JwtEncoder jwtEncoder;

    public String generateTokenClaimSet(String nome, String sobrenome, String role){
        var expiresIn = 3000L; // Tempo de expiração

        var claims = JwtClaimsSet.builder()
                    .issuer("authService")
                    .subject(nome + " " + sobrenome)
                    .claim("authorities", List.of("ROLE_" + role.toUpperCase()))
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(expiresIn))
                    .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
