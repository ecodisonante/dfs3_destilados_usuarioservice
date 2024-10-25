package com.ecodisonante.destilado_express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecodisonante.destilado_express.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // buscar usuario por email
    Usuario findByEmail(String email);
}
