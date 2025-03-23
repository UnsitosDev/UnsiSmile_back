FROM gradle:jdk21-jammy as build

# Crear directorio de trabajo
WORKDIR /app

# Copiar solo los archivos esenciales para instalar dependencias primero
COPY build.gradle settings.gradle ./

# Descargar dependencias
RUN gradle dependencies --no-daemon || return 0

# Copiar el código fuente y construir la aplicación
COPY . .
RUN gradle build --no-daemon --no-build-cache

# Crear imagen final para la aplicación
FROM openjdk:21-jdk-slim    

# Instalar dependencias gráficas necesarias
RUN apt-get update && \
    apt-get install -y libfreetype6 libfontconfig1 && \
    rm -rf /var/lib/apt/lists/*

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/build/libs/unsiSmile-0.0.1-SNAPSHOT.jar app.jar

# Crear directorio de logs
RUN mkdir -p /var/log/unsismile && \
    chmod 777 /var/log/unsismile

# Exponer el puerto 8080
EXPOSE 8082              

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]