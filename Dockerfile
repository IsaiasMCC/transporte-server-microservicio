# Usa una imagen base de OpenJDK (puedes elegir la versión que prefieras)
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por el build de Maven al contenedor
COPY target/transporte-service-0.0.1-SNAPSHOT.jar transporte-server.jar

# Expone el puerto en el que la aplicación Spring Boot estará escuchando
EXPOSE 8083

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app/transporte-server.jar"]

