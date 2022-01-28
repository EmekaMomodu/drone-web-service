# Drone Web Service

This is a RESTFul web service with endpoints to handle requests for managing drones used for transporting medications.


## Prerequisites

The following installation(s) is(are) needed on your local environment to run the service.
* [Docker](https://docs.docker.com/get-docker/)

#### ALTERNATIVELY (if Docker is not readily available)
* [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)


## Getting Started

These instructions will get the service up & running on your local environment.

* For Docker Prerequisite Satisfied:
1. Open terminal in project's root directory i.e where the file named `Dockerfile` is located.
2. Run command `docker build -t drone-web-service .` to build the docker image.
3. Run command `docker run -p 8080:8080 -d drone-web-service` to spin up a container for the created image.
4. Services are now available at `http://localhost:8080/...`. View documentation for available endpoints.

* For Java 8 & Maven Prerequisite Satisfied:
1. Open terminal in project's root directory.
2. Run command `mvn clean install` to install dependencies, build and package.
3. Rum command `java -Dspring.profiles.active=default -jar ./target/drone-web-service.jar`
4. Services are now available at `http://localhost:8080/...`. View documentation for available endpoints.

## API Documentation
View api documentation at
* Postman documentation located at [Drone-Web-Service.postman_collection.json](https://github.com/EmekaMomodu/drone-web-service/blob/main/documentation/Drone-Web-Service.postman_collection.json)
* Postman documentation published at [https://documenter.getpostman.com/view/9270015/UVeCPTHk ](https://documenter.getpostman.com/view/9270015/UVeCPTHk)
* Swagger documentation at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (while the service is running)


## Database Console
View H2 in-memory database at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) login details specified at [application.properties](https://github.com/EmekaMomodu/drone-web-service/blob/main/src/main/resources/application.properties) (while the service is running)

