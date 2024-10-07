package org.acme.com.example.entity.repository;

import org.acme.com.example.entity.Asistencia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AsistenciaRepository implements PanacheRepository<Asistencia> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
