# Microservicios Usuarios

Microservicio Springboot con integración Mysql, Java18 y Docker

## Comandos Docker de Instalación 🔧

- Crear Network y listar su creación (esta red se compartirá entre contenedores)
```
docker network create spring
docker network ls
```
- Crear Contenedor de BD que tendrá los usuarios de la aplicación
```
docker run -d -p 3306:3306 --name mysqldb --network spring -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=msvc_usuarios mysql:8
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

docker build -t msvc_usuarios . -f .\msvc-usuarios\Dockerfile
```

- Activar contenedor para probar la aplicación
```
docker container run --network spring --name usuarioscontainer -p 8001:8001 -d msvc_usuarios
docker container logs -f c74ca7e43ce6
```

- Si se desea replicar la cantidad e contenedores deseados en base a una imagen, se puede utilizar
```
docker container run --network spring --name usuarioscontainer-2 -p 8081:8001 -d msvc_usuarios
```

- Crear una imagen con nombre y Activar un contenedor con nombre
```
docker build -t usuarios:v2 .
docker run -p 8001:80001 -d --name msvc-usuarios usuarios:v2
```

## Autor ✒️

* **Domingo Rondon** - *Curso Udemy Microservicios* - [Domingo Rondon](https://github.com/drondon87)