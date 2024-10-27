package com.destilado_express.usuarioservice.service;

public interface JwtService {
    String generateToken(String username);

    boolean validateToken(String token);

    String extractUsername(String token);
}
