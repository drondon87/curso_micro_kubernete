FROM openjdk:18-alpine

WORKDIR /app

COPY ./target/msvc-auth-0.0.1-SNAPSHOT.jar msvc-auth-0.0.1-SNAPSHOT.jar

ENV PORT 9000
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "msvc-auth-0.0.1-SNAPSHOT.jar"]