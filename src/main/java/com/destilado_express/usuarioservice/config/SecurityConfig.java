package com.destilado_express.usuarioservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.destilado_express.usuarioservice.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
    private JwtRequestFilter jwtRequestFilter;  // El filtro que se encargará de validar el JWT

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)  // Desactivar CSRF (si es necesario, dependiendo de tu arquitectura)
            .authorizeHttpRequests((req) -> req
                .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()  // Permitir acceso al GET de usuarios (ajustar según tus necesidades)
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()  // Permitir acceso al login sin autenticación
                .requestMatchers(HttpMethod.PUT, "/api/usuarios").authenticated()  // Requiere autenticación para actualizar usuarios
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios").hasRole("ADMIN")  // Solo admin puede borrar usuarios
                .anyRequest().authenticated())  // Todas las demás solicitudes requieren autenticación
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Agregar el filtro para validar JWT

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}