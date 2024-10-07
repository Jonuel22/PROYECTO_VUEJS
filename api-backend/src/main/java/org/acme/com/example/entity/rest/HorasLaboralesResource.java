package org.acme.com.example.entity.rest;

import org.acme.com.example.entity.HorasLaborales;
import org.acme.com.example.entity.repository.HorasLaboralesRepository;
import org.acme.com.example.entity.repository.EmpleadoRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/horas-laborales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HorasLaboralesResource {

    @Inject
    HorasLaboralesRepository horasLaboralesRepository;

    @Inject
    EmpleadoRepository empleadoRepository;

    // Obtener todas las horas laborales
    @GET
    public List<HorasLaborales> obtenerHorasLaborales() {
        return horasLaboralesRepository.listAll();
    }

    // Registrar nuevas horas laborales
    @POST
    public Response registrarHorasLaborales(HorasLaborales horasLaborales) {
        if (empleadoRepository.findById(horasLaborales.getEmpleado().getId()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Empleado no existe").build();
        }
        horasLaboralesRepository.persist(horasLaborales);
        return Response.status(Response.Status.CREATED).entity(horasLaborales).build();
    }

    // Obtener horas laborales por su ID
    @GET
    @Path("/{id}")
    public Response obtenerHorasLaboralesPorId(@PathParam("id") Long id) {
        HorasLaborales horasLaborales = horasLaboralesRepository.findById(id);
        if (horasLaborales == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(horasLaborales).build();
    }

    // Actualizar horas laborales existentes
    @PUT
    @Path("/{id}")
    public Response actualizarHorasLaborales(@PathParam("id") Long id, HorasLaborales horasLaborales) {
        HorasLaborales horasLaboralesExistente = horasLaboralesRepository.findById(id);
        if (horasLaboralesExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        horasLaboralesExistente.setFecha(horasLaborales.getFecha());
        horasLaboralesExistente.setHorasTrabajadas(horasLaborales.getHorasTrabajadas());
        horasLaboralesRepository.persist(horasLaboralesExistente);
        return Response.ok(horasLaboralesExistente).build();
    }

    // Eliminar horas laborales por su ID
    @DELETE
    @Path("/{id}")
    public Response eliminarHorasLaborales(@PathParam("id") Long id) {
        boolean eliminado = horasLaboralesRepository.deleteById(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
