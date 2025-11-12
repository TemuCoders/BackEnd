# Imagen con Java 25
FROM eclipse-temurin:25-jdk

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR
COPY target/workstation-0.0.1-SNAPSHOT.jar app.jar

# Render usa una variable PORT, la pasamos a Spring
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
