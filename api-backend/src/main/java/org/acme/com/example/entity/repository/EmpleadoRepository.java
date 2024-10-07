package org.acme.com.example.entity.repository;

import org.acme.com.example.entity.Empleado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmpleadoRepository implements PanacheRepository<Empleado> {
    // Métodos personalizados para consultas adicionales pueden agregarse aquí
}
