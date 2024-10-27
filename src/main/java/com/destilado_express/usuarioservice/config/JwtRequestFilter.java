package com.destilado_express.usuarioservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.destilado_express.usuarioservice.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@SuppressWarnings("null")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Excluir login de la validación del token
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login");  
    }

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        String role = null;

        // Extraer el token JWT del encabezado Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.extractUsername(token);
            role = jwtService.extractRole(token);
        }

        // Token valido, contiuar
        if (username != null && role != null && jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
        } else {
            // Token invalido, rechazar
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        }
    }
}
