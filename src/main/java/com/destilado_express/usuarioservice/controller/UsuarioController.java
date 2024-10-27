package com.destilado_express.usuarioservice.controller;

import com.destilado_express.usuarioservice.model.Usuario;
import com.destilado_express.usuarioservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener usuario por email
    @GetMapping("/email")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.getUsuarioByEmail(email);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // // Crear un nuevo usuario
    // @PostMapping
    // public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario, Authentication authentication) {
    //     String rolUsuarioActual = authentication.getAuthorities().iterator().next().getAuthority();
        
    //     // Si el usuario actual es admin, puede asignar rol, de lo contrario asignar CLIENTE por defecto
    //     if ("ROLE_ADMIN".equals(rolUsuarioActual)) {
    //         // Admin puede asignar cualquier rol
    //         return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.CREATED);
    //     } else {
    //         // No admin, forzar rol CLIENTE
    //         usuario.setRol(rolRepository.findByNombre("CLIENTE"));
    //         return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.CREATED);
    //     }
    // }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
