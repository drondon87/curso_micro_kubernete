# Microservicios Usuarios

Microservicio Springboot con integración Mysql, Java18 y Docker

## Comandos Docker de Instalación 🔧

- Crear Network y listar su creación
```
docker network create usuario-mysql
docker network ls
```
- Crear Contenedor de BD que tendrá los usuarios de la aplicación
```
docker container run --name mysqldb --network usuario-mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=msvc_usuarios -d mysql:8.0.0
docker container logs -f mysqldb
```
- Ejecutar contenedor de BD para verificar si la BD está activa
```
docker container exec -it mysqldb bash

mysql -uroot -p1234

show databases;
```

- Crear Imagen en el Docker de la aplicación
```
docker build -t msvc_usuarios .
```

- Activar contenedor para probar la aplicación
```
docker container run --network usuario-mysql --name msvc_usuarios-jdbc-container -p 8001:8001 -d msvc_usuarios
docker container logs -f c74ca7e43ce6
```

## Autor ✒️

* **Domingo Rondon** - *Curso Udemy Microservicios* - [Domingo Rondon](https://github.com/drondon87)