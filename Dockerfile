FROM gradle:jdk21-jammy

# Variables de entorno para la versión de Gradle
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}
ENV UNSISMILE_DATABASE=jdbc:mariadb://db:3306/unsis_smile?useSSL=false&serverTimezone=UTC
ENV DATABASE_USERNAME=root
ENV DATABASE_PASSWORD=password
ENV DATABASE_PLATFORM=org.hibernate.dialect.MariaDBDialect
ENV DRIVER_CLASS_NAME=org.mariadb.jdbc.Driver
ENV JWT_SECRET=yOksepqllu0yhyfFGKWOhjd8rISN23HypgnkDaMy/SduH2pj3jtGe2Eu9mxoxAe5QIPvkGB2RXyGtd/nLAbTnw/2gOq1pW2rbGJ5hshLK2XTBqE4DtsLsWkB2Y9TzmZK5MGh9VjYVVH2rfEAtPxUp4F6nHbumg1Ii/T+ut3F3G/qGhYS5jGKXaGDJIGCjXQntcX5dRMHDa5kU1L2S+hYlc7YIUuDgsItGJ9rob10FhWjn7u1EGSFq7NoIaLmrmaB1PIk1XA3oZ4fC/8rpz9OQZj/Wv9GRzkEvhPARH3n81XnDj4OXoTST3vVRyOaTphIoht+Rag9lgeQB7GAiqEcmJUB63LQNyUwRdveMk+4sQA=

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
