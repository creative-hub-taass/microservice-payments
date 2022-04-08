# syntax=docker/dockerfile:1.2
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
USER root
COPY pom.xml .
COPY src ./src
RUN  mvn -B -Dmaven.test.skip clean package

#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 9090
COPY --from=build /app/target/*.jar appbootrest.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "appbootrest.jar"]