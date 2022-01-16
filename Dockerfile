ARG version=3.6.3-jdk-8-slim
FROM maven:${version}

WORKDIR /app
COPY ./pom.xml /app/pom.xml
ADD ./src /app/src

RUN mvn clean install

EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.profiles.active=default", "-jar", "/app/target/drone-web-service.jar"]
