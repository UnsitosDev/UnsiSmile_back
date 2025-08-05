# UnsiSmile Back

Este es el repositorio del backend de UnsiSmile, una aplicación para la gestión de historiales clínicos odontológicos.

## Requisitos

- Docker
- Docker Compose

## Configuración

### ignorar el archivo docker-compose

```sh
git update-index --skip-worktree docker-compose.yml
```

### Variables de Entorno

Asegúrate de configurar las siguientes variables de entorno en tu archivo `docker-compose.yml` o en tu archivo `application.properties` local:

#### Configuración CORS

La aplicación utiliza la variable de entorno `CORS_ALLOWED_ORIGINS` para definir los orígenes permitidos. Los orígenes deben estar separados por comas sin espacios.

#### Configuración en Docker Compose

Agrega la siguiente configuración en tu archivo `docker-compose.yml`:

```yaml
services:
  app:
    environment:
      # ... otras variables de entorno
      - CORS_ALLOWED_ORIGINS=http://localhost:8081,https://unsismile.unsis.edu.mx
```

#### Configuración para desarrollo local

Si estás ejecutando la aplicación localmente sin Docker, puedes configurar la variable de entorno en tu sistema operativo o en la configuración de tu IDE.

##### En Linux/macOS:
```bash
export CORS_ALLOWED_ORIGINS="http://localhost:8081,https://unsismile.unsis.edu.mx"
```

##### En Windows (CMD):
```cmd
set CORS_ALLOWED_ORIGINS=http://localhost:8081,https://unsismile.unsis.edu.mx
```

#### Orígenes por defecto

Si la variable `CORS_ALLOWED_ORIGINS` no está definida, la aplicación no permitirá ningún origen por defecto. Asegúrate de configurar al menos un origen válido.

#### Reinicio necesario

Después de realizar cambios en la configuración de CORS, es necesario reiniciar la aplicación para que los cambios surtan efecto.

Puedes generar un JWT secret utilizando el siguiente comando en tu terminal:

```sh
openssl rand -base64 256
```

### Levantar el Proyecto

Sigue estos pasos para levantar el proyecto correctamente:

1. Clona el repositorio:

2. Crea y configura el archivo `docker-compose.yml` con las variables de entorno mencionadas anteriormente.

3. Levanta los servicios de Docker:
   `sh
    docker-compose up -d
    `

4. Verifica que los contenedores estén corriendo:
   `sh
    docker-compose ps
    `

5. Accede a la aplicación en tu navegador en `http://localhost:8080`.

### Logs y Auditoría

El sistema de logging utiliza Log4j2 para separar los mensajes según su nivel en archivos distintos, ubicados en el directorio definido por la propiedad `APP_LOG_ROOT` (por defecto, `/var/log/unsismile`):

- **debug.log**: Registra mensajes de nivel DEBUG.
- **info.log**: Registra mensajes de nivel INFO.
- **error.log**: Registra mensajes de nivel ERROR.

### Visualización de Logs

Si ejecutas la aplicación en un entorno local o mediante Docker, asegúrate de que el directorio `/var/log/unsismile` exista y sea accesible. Por ejemplo, en Linux puedes utilizar el comando `tail` para visualizar los logs en tiempo real (estando dentro del directorio):

```bash
tail -f debug.log
tail -f info.log
tail -f error.log
```

### Notas Adicionales

- Para crear usuarios use el scrip poblados_usuarios.sql del repositorio de base de datos.
- Para entorno de desarrollo se recomienda usar `docker-compose.dev.yml` donde solo tiene la base de datos y configurar el application.properties localmente.
- Asegúrate de tener los puertos `8082` y `3306` disponibles en tu máquina.
- Puedes detener los servicios en cualquier momento con:
  `sh
      docker-compose down
      `

Con estos pasos, deberías poder levantar y ejecutar el backend de UnsiSmile correctamente.
