# Usa una imagen de Java 21
FROM eclipse-temurin:21-jre-jammy

# Argumento para pasar el jar
ARG JAR_FILE=target/*.jar

# Copia el jar al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "/app.jar"]
