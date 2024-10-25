package com.ecodisonante.destilado_express.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecodisonante.destilado_express.model.Producto;
import com.ecodisonante.destilado_express.model.Rol;
import com.ecodisonante.destilado_express.model.Usuario;
import com.ecodisonante.destilado_express.repository.ProductoRepository;
import com.ecodisonante.destilado_express.repository.RolRepository;
import com.ecodisonante.destilado_express.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {

        // Crear datos parametricos
        Rol admin = new Rol(1L, "ADMIN");
        Rol cliente = new Rol(2L, "CLIENTE");

        // Poblar Roles
        if (rolRepository.count() == 0) {
            rolRepository.save(admin);
            rolRepository.save(cliente);
        }

        // Poblar Producto
        if (productoRepository.count() == 0) {
            productoRepository
                    .save(new Producto("Whisky", "Whisky escocés premium", "imagen_whisky.jpg", 15000, 0, 10, true));
            productoRepository
                    .save(new Producto("Vodka", "Vodka ruso tradicional", "imagen_vodka.jpg", 13000, 0, 20, true));
            productoRepository.save(new Producto("Ron", "Ron añejo cubano", "imagen_ron.jpg", 12000, 0, 15, true));
        }

        // Poblar Usuarios
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Juan Perez", "juan@example.com", "Calle Falsa 123", admin));
            usuarioRepository.save(new Usuario("Ana Gomez", "ana@example.com", "Avenida Siempre Viva 456", cliente));
        }
    }
}
