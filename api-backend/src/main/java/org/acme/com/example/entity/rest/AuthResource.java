package org.acme.com.example.entity.rest;

import org.acme.com.example.entity.Usuario;
import org.acme.com.example.entity.repository.UsuarioRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioRepository usuarioRepository;

    // Crear un pool de hilos para manejar procesos en paralelo
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @POST
    public Response login(Credentials credentials) {
        // Verificar si el email o el password son nulos o están vacíos
        if (credentials.getEmail() == null || credentials.getEmail().trim().isEmpty() ||
            credentials.getPassword() == null || credentials.getPassword().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("El email o la contraseña no pueden ser nulos o vacíos")
                .build();
        }

        // Limpiar el email y la contraseña para evitar espacios en blanco accidentales
        String email = credentials.getEmail().trim();
        String password = credentials.getPassword().trim();

        // Buscar al usuario en la base de datos por correo (case-insensitive)
        Optional<Usuario> usuarioOpt = usuarioRepository.find("LOWER(correo)", email.toLowerCase()).firstResultOptional();

        if (usuarioOpt.isEmpty()) {
            // Si no existe un usuario con el correo proporcionado
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Correo o contraseña incorrectos")
                .build();
        }

        Usuario usuario = usuarioOpt.get();

        // Comparar la contraseña enviada con la almacenada en la base de datos
        if (!usuario.getPassword().equals(password)) {
            // Si la contraseña no coincide
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Correo o contraseña incorrectos")
                .build();
        }

        // Si las credenciales son válidas, devolver la información del usuario sin token
        UserInfo user = new UserInfo(usuario.getNombre(), usuario.getCorreo());

        // Ejecutar el envío de la notificación a Vert.x de manera asíncrona usando un CompletableFuture
        CompletableFuture.runAsync(() -> sendNotificationToVertx(usuario.getCorreo()), executorService)
            .exceptionally(ex -> {
                // Manejar cualquier excepción que ocurra durante el proceso asíncrono
                ex.printStackTrace();
                return null;
            });

        return Response.ok()
            .entity(user)  // Devolver solo los datos del usuario
            .build();
    }

    // Método para enviar la señal a Vert.x en Azure
    private void sendNotificationToVertx(String email) {
        try {
            // URL de la API de Vert.x en Azure
            URL url = new URL("https://apivert-d3h7d9hkc'faughtf.westus2-01.azurewebsites.net/sendNotification");

            // Crear conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            // Cuerpo de la solicitud con la información del usuario
            String input = String.format("{\"email\":\"%s\",\"message\":\"Inicio de sesión exitoso\"}", email);

            // Enviar el cuerpo de la solicitud
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            // Verificar la respuesta de la API de Vert.x
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Error al enviar señal a Vert.x: " + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clases auxiliares
    public static class Credentials {
        private String email;
        private String password;

        // Getters y setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class UserInfo {
        public String name;
        public String email;

        public UserInfo(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
