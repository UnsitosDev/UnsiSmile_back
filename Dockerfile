FROM gradle:jdk21-jammy

# Variables de entorno para la versión de Gradle
ENV GRADLE_VERSION 8.6
ENV GRADLE_HOME /opt/gradle
ENV PATH ${GRADLE_HOME}/bin:${PATH}

# Crear directorio para la aplicación
WORKDIR /app

# Copiar el archivo build.gradle y settings.gradle
COPY build.gradle settings.gradle ./

# Descargar dependencias de Gradle
RUN gradle build --no-daemon || return 0

# Copiar el código fuente de la aplicación
COPY src ./src

# Construir la aplicación
RUN gradle build --no-daemon

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "build/libs/unsiSmile-0.0.1-SNAPSHOT.jar"]
