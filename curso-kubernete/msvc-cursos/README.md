# Microservicios Cursos

Microservicio Springboot con integración Postgress, Java18 y Docker

## Comandos Docker de Instalación 🔧

- Crear Network y listar su creación (se debe verificar si anteriormente fue creada, si es así, se omiten estos pasos)
```
docker network create spring
docker network ls
```
- Crear Contenedor de BD que tendrá los usuarios de la aplicación
```
docker container run --name postgresqlDB --network net-cursos-postgresql -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=msvc_cursos -d -v data-postgres:/var/lib/postgresql/data postgres:10.0
docker container logs -f postgresqlDB
```
- Ejecutar contenedor de BD para verificar si la BD está activa
```
docker container exec -it postgresqlDB bash
```

- Crear Imagen en el Docker de la aplicación
```
docker build -t msvc_cursos .
```

- Activar contenedor para probar la aplicación
```
docker container run --network spring --name cursoscontainer -p 8002:8002 -d msvc_cursos
docker container run --network spring --name cursoscontainer -p 8002:8002 --env-file .\.env -d msvc_cursos
docker container logs -f 06a67471cd0e
```

- Si se desea replicar la cantidad e contenedores deseados en base a una imagen, se puede utilizar
```
docker container run --network spring --name cursoscontainer2 -p 8082:8002 -d msvc_cursos
```

## Autor ✒️

* **Domingo Rondon** - *Curso Udemy Microservicios* - [Domingo Rondon](https://github.com/drondon87)