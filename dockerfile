# Usa una imagen de Java
FROM openjdk:17-jdk-slim

# Argumento para pasar el jar
ARG JAR_FILE=target/*.jar

# Copia el jar al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto (ajústalo si tu app corre en otro)
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "/app.jar"]