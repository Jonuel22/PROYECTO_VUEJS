# Usar una imagen base con Java 19
FROM eclipse-temurin:19-jre-alpine

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar todos los archivos necesarios generados por Quarkus
COPY target/quarkus-app/lib/ /app/lib/
COPY target/quarkus-app/app/ /app/app/
COPY target/quarkus-app/quarkus/ /app/quarkus/
COPY target/quarkus-app/quarkus-run.jar /app/

# Exponer el puerto en el que la aplicación va a correr
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/quarkus-run.jar"]
