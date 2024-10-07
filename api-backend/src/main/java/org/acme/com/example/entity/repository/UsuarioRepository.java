package org.acme.com.example.entity.repository;

import org.acme.com.example.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    // Métodos personalizados para consultas adicionales pueden agregarse aquí
}
