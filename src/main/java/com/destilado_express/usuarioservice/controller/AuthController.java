package com.destilado_express.usuarioservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.destilado_express.usuarioservice.model.AuthRequest;
import com.destilado_express.usuarioservice.model.AuthResponse;
import com.destilado_express.usuarioservice.service.JwtService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Autenticar usuario");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            // Si la autenticación es correcta, generar el token JWT
            String token = jwtService.generateToken(authentication.getName());

            // Retornar el token en la respuesta
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            log.severe(e.getMessage() + "\n" + e.getStackTrace());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }
}
