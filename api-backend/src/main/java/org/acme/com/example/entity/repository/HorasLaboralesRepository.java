package org.acme.com.example.entity.repository;

import org.acme.com.example.entity.HorasLaborales;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HorasLaboralesRepository implements PanacheRepository<HorasLaborales> {
    // Métodos personalizados para consultas adicionales pueden agregarse aquí
}
