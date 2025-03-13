# UnsiSmile Back

Este es el repositorio del backend de UnsiSmile, una aplicación para la gestión de historiales médicos odontológicos.

## Requisitos

- Docker
- Docker Compose

## Configuración

### ignorar el archivo docker-compose
```sh
git update-index --skip-worktree docker-compose.yml
```

### Variables de Entorno

Asegúrate de configurar las siguientes variables de entorno en tu archivo `docker-compose.yml`:

```yaml
services:
    app:
        environment:
            SPRING_DATASOURCE_PASSWORD: your_database_password
            JWT_SECRET: your_jwt_secret
    db:
        environment:
            MYSQL_ROOT_PASSWORD: your_root_password
```
Puedes generar un JWT secret utilizando el siguiente comando en tu terminal:

```sh
openssl rand -base64 256
```


### Levantar el Proyecto

Sigue estos pasos para levantar el proyecto correctamente:

1. Clona el repositorio:
        ```sh
        git clone https://github.com/tu_usuario/UnsiSmile_back.git
        cd UnsiSmile_back
        ```

2. Crea y configura el archivo `docker-compose.yml` con las variables de entorno mencionadas anteriormente.

3. Levanta los servicios de Docker:
        ```sh
        docker-compose up -d
        ```

4. Verifica que los contenedores estén corriendo:
        ```sh
        docker-compose ps
        ```

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

- Asegúrate de tener los puertos `8082` y `3306` disponibles en tu máquina.
- Puedes detener los servicios en cualquier momento con:
        ```sh
        docker-compose down
        ```

Con estos pasos, deberías poder levantar y ejecutar el backend de UnsiSmile correctamente.

