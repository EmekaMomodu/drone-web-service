# Drone Web Service
This is a dockerized web service with APIs to handle requests for managing drones used for transporting medications.

## Setup
1. Open terminal in project's root directory where the file `Dockerfile` is located.

2. Build the docker image, run command `docker build -t drone-web-service .`

3. Spin up a container from the created image, run command `docker run -p 8080:8080 -d drone-web-service`
