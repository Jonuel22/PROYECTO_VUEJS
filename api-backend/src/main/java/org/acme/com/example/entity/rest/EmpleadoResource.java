package org.acme.com.example.entity.rest;

import org.acme.com.example.entity.Empleado;
import org.acme.com.example.entity.repository.EmpleadoRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/empleados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpleadoResource {

    @Inject
    EmpleadoRepository empleadoRepository;

    // Obtener todos los empleados
    @GET
    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.listAll();
    }

    // Registrar un nuevo empleado
    @POST
    @Transactional  // Inicia una transacción para insertar datos
    public Response registrarEmpleado(Empleado empleado) {
        empleadoRepository.persist(empleado);
        return Response.status(Response.Status.CREATED).entity(empleado).build();
    }

    // Obtener un empleado por su ID
    @GET
    @Path("/{id}")
    public Response obtenerEmpleadoPorId(@PathParam("id") Long id) {
        Empleado empleado = empleadoRepository.findById(id);
        if (empleado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(empleado).build();
    }

    // Actualizar un empleado existente
    @PUT
    @Path("/{id}")
    @Transactional  // Inicia una transacción para actualizar datos
    public Response actualizarEmpleado(@PathParam("id") Long id, Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id);
        if (empleadoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setApellido(empleado.getApellido());
        empleadoExistente.setCorreo(empleado.getCorreo());
        empleadoExistente.setTelefono(empleado.getTelefono());
        empleadoExistente.setDireccion(empleado.getDireccion());
        empleadoRepository.persist(empleadoExistente);
        return Response.ok(empleadoExistente).build();
    }

    // Eliminar un empleado por su ID
    @DELETE
    @Path("/{id}")
    @Transactional  // Inicia una transacción para eliminar datos
    public Response eliminarEmpleado(@PathParam("id") Long id) {
        boolean eliminado = empleadoRepository.deleteById(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
