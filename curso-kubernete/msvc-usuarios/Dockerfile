FROM openjdk:18-alpine

WORKDIR /app

COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar msvc-usuarios-0.0.1-SNAPSHOT.jar

ENV PORT 8001
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]