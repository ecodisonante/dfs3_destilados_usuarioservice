package com.ecodisonante.destilado_express.service;

import com.ecodisonante.destilado_express.model.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> getAllUsuarios();

    Usuario getUsuarioById(Long id);

    Usuario crearUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
}
