# 1. Usamos una imagen de OpenJDK 25 (ajustado a tu versión)
FROM eclipse-temurin:25-jdk-jammy

# 2. Buenas prácticas DevSecOps: Actualizar dependencias del SO base
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

# 3. Buenas prácticas DevSecOps: Crear un usuario no-root
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# 4. Exponemos el puerto real de nuestra aplicación
EXPOSE 8085

# 5. Copiamos el ejecutable
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 6. Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]