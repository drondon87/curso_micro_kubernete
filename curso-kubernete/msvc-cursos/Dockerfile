FROM openjdk:18-alpine

WORKDIR /app

COPY ./target/msvc-cursos-0.0.1-SNAPSHOT.jar msvc-cursos-0.0.1-SNAPSHOT.jar

ENV PORT 8001
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]